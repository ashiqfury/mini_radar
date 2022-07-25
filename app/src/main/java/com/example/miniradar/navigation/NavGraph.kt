package com.example.miniradar.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.miniradar.screen.AgentsCardScreen
import com.example.miniradar.data.model.Person
import com.example.miniradar.screen.AgentsDetailsScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    liveDataPersonList: LiveData<List<Person>>
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            AgentsCardScreen(navController = navController, liveDataPersonList)
        }
        composable(
            route = Screen.Detail.route,
        ) {
            AgentsDetailsScreen(navController = navController)
        }
    }
}