package com.github.sirdx.plainalarm.presentation.screen.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(
    navController: NavHostController
) {
    val viewModel = hiltViewModel<HomeViewModel>()

    HomeScreenContent()
}

@Composable
private fun HomeScreenContent(

) {

}
