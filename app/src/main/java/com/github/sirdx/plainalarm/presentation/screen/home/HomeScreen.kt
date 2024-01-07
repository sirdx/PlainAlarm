package com.github.sirdx.plainalarm.presentation.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.sirdx.plainalarm.core.Resource
import com.github.sirdx.plainalarm.domain.model.Alarm
import com.github.sirdx.plainalarm.domain.model.AlarmId
import com.github.sirdx.plainalarm.presentation.navigation.GetOnceResult
import com.github.sirdx.plainalarm.presentation.navigation.Screen

@Composable
fun HomeScreen(
    navController: NavHostController
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val alarms by viewModel.alarms.collectAsState()

    navController.currentBackStackEntry?.GetOnceResult<AlarmId>(AlarmId.toString()) {
        viewModel.addAlarm(it)
    }

    HomeScreenContent(
        alarms = alarms,
        onAddAlarm = {
            navController.navigate(Screen.AddAlarm.route)
        },
        onAlarmToggle = viewModel::toggleAlarm,
        onAlarmDelete = viewModel::deleteAlarm
    )
}

@Composable
private fun HomeScreenContent(
    alarms: Resource<List<Alarm>>,
    onAddAlarm: () -> Unit,
    onAlarmToggle: (Alarm, Boolean) -> Unit,
    onAlarmDelete: (Alarm) -> Unit
) {
    when (alarms) {
        is Resource.Loading -> HomeScreenLoading()

        is Resource.Success -> HomeScreenAlarms(
            alarms = alarms.data,
            onAddAlarm = onAddAlarm,
            onAlarmToggle = onAlarmToggle,
            onAlarmDelete = onAlarmDelete
        )

        is Resource.Error -> HomeScreenError(
            message = alarms.message ?: "Unknown error"
        )
    }
}

@Composable
private fun HomeScreenLoading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Loading...")
    }
}

@Composable
private fun HomeScreenError(
    message: String
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Warning,
            contentDescription = "An error occurred",
            tint = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = message
        )
    }
}

@Composable
private fun HomeScreenAlarms(
    alarms: List<Alarm>,
    onAddAlarm: () -> Unit,
    onAlarmToggle: (Alarm, Boolean) -> Unit,
    onAlarmDelete: (Alarm) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "Add") },
                icon = { Icon(imageVector = Icons.Filled.Add, contentDescription = "Add an alarm") },
                onClick = onAddAlarm
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(15.dp)
        ) {
            item {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = "PlainAlarm",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(15.dp))
            }
            items(
                items = alarms,
                key = { it.id }
            ) { alarm ->
                HomeScreenAlarmCard(
                    alarm = alarm,
                    onAlarmToggle = { toggle ->
                        onAlarmToggle(alarm, toggle)
                    },
                    onAlarmDelete = {
                        onAlarmDelete(alarm)
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeScreenAlarmCard(
    alarm: Alarm,
    onAlarmToggle: (Boolean) -> Unit,
    onAlarmDelete: () -> Unit
) {
    val haptics = LocalHapticFeedback.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { },
                onLongClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    onAlarmDelete()
                }
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
            ) {
                Text(
                    text = alarm.time.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = alarm.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Switch(
                checked = alarm.isEnabled,
                onCheckedChange = onAlarmToggle
            )
        }
    }
}
