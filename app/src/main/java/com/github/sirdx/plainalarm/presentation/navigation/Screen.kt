package com.github.sirdx.plainalarm.presentation.navigation

sealed class Screen(val route: String) {

    data object Home : Screen("home")
    data object AddAlarm : Screen("add_alarm")
}