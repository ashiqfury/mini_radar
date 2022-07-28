package com.example.miniradar.navigation

sealed class Screen(val route: String) {
    object Home: Screen(route = "home_screen")
    object Detail: Screen(route = "detail_screen")

    object Experiment: Screen(route = "experimental_screen")
}
