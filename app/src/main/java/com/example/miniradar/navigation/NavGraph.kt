package com.example.miniradar.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.miniradar.screen.AgentsCardScreen
import com.example.miniradar.data.model.Person
import com.example.miniradar.screen.AgentsDetailsScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
//import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    liveDataPersonList: LiveData<List<Person>>
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(
            route = Screen.Home.route,
//            enterTransition = { _, _ ->
//                fadeIn(animationSpec = tween(2000))
//            },
//            exitTransition = { _, _ ->
//                fadeOut(animationSpec = tween(2000))
//            }
        ) {
            AgentsCardScreen(navController = navController, liveDataPersonList)
        }
        composable(
            route = Screen.Detail.route,
        ) {
            AgentsDetailsScreen(navController = navController)
        }
        composable(
            route = Screen.Experiment.route,
        ) {
            AgentsDetailsScreen(navController = navController)
        }
    }
}