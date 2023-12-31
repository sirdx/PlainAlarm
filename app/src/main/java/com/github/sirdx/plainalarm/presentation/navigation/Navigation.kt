package com.github.sirdx.plainalarm.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.github.sirdx.plainalarm.presentation.screen.addalarm.AddAlarmScreen
import com.github.sirdx.plainalarm.presentation.screen.home.HomeScreen

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                navController = navController
            )
        }
        composable(route = Screen.AddAlarm.route) {
            AddAlarmScreen(
                navController = navController
            )
        }
    }
}

@Composable
fun <T> NavBackStackEntry.GetOnceResult(resultKey: String, onResult: (T) -> Unit) {
    val resultLiveData = savedStateHandle.getLiveData<T>(resultKey)

    resultLiveData.observeAsState().value?.let {
        resultLiveData.value = null
        onResult(it)
    }
}
