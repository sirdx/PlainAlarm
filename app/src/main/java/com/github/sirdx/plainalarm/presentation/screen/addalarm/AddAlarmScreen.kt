package com.github.sirdx.plainalarm.presentation.screen.addalarm

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.sirdx.plainalarm.domain.model.AlarmId
import com.github.sirdx.plainalarm.presentation.component.AlarmTimePicker
import com.github.sirdx.plainalarm.presentation.navigation.Screen
import kotlinx.datetime.LocalTime

@Composable
fun AddAlarmScreen(
    navController: NavHostController
) {
    val viewModel = hiltViewModel<AddAlarmViewModel>()
    val addedAlarmId by viewModel.addedAlarmId.collectAsState()
    val canSubmit by viewModel.canSubmit.collectAsState()
    val formName by viewModel.formName.collectAsState()
    val formTime by viewModel.formTime.collectAsState()

    LaunchedEffect(addedAlarmId) {
        addedAlarmId?.let { alarmId ->
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set(AlarmId.toString(), alarmId)

            navController.popBackStack(
                route = Screen.Home.route,
                inclusive = false
            )
        }
    }

    AddAlarmScreenContent(
        canSubmit = canSubmit,
        formName = formName,
        formTime = formTime,
        onNameChange = viewModel::onNameChange,
        onTimeChange = viewModel::onTimeChange,
        onAddAlarm = viewModel::addAlarm,
        onNavigateBack = {
            navController.popBackStack(
                route = Screen.Home.route,
                inclusive = false
            )
        }
    )
}

@Composable
private fun AddAlarmScreenContent(
    canSubmit: Boolean,
    formName: String,
    formTime: LocalTime,
    onNameChange: (String) -> Unit,
    onTimeChange: (LocalTime) -> Unit,
    onNavigateBack: () -> Unit,
    onAddAlarm: () -> Unit
) {
    Column(
        modifier = Modifier.padding(15.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Go back"
                )
            }
            Text(
                modifier = Modifier.padding(5.dp),
                text = "Add an alarm",
                style = MaterialTheme.typography.titleLarge
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = formName,
            onValueChange = onNameChange,
            label = { Text(text = "Name") }
        )
        Spacer(modifier = Modifier.height(10.dp))
        AlarmTimePicker(
            modifier = Modifier.fillMaxWidth(),
            time = formTime,
            onTimeChange = onTimeChange
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = canSubmit,
            onClick = onAddAlarm
        ) {
            Text(text = "Add an alarm")
        }
    }
}
