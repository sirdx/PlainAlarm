package com.github.sirdx.plainalarm.presentation.screen.addalarm

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun AddAlarmScreen(
    navController: NavHostController
) {
    val viewModel = hiltViewModel<AddAlarmViewModel>()

    AddAlarmScreenContent()
}

@Composable
private fun AddAlarmScreenContent(

) {

}
