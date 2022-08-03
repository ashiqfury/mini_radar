package com.example.miniradar.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.example.miniradar.data.model.Person
import com.example.miniradar.data.model.SamplePerson
import com.example.miniradar.screen.AgentsCardScreen
import com.example.miniradar.screen.AgentsDetailsScreen
import com.example.miniradar.screen.AgentsSearchScreen
import com.example.miniradar.sharedelements.SharedElementsRoot

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    liveDataPersonList: LiveData<List<SamplePerson>>
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(
            route = Screen.Home.route,
        ) {
            AgentsCardScreen(navController = navController, personLiveData = liveDataPersonList)
        }
        composable(
            route = Screen.Detail.route + "/{personId}",
            arguments = listOf(
                navArgument("personId") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { entry ->
            entry.arguments?.getInt("personId")?.let {
                AgentsDetailsScreen(
                    navController = navController,
                    personLiveData = liveDataPersonList,
                    personId = it
                )
            }
        }
        composable(
            route = Screen.Search.route,
        ) {
            AgentsSearchScreen(navController = navController, personLiveData = liveDataPersonList)
        }
    }
}