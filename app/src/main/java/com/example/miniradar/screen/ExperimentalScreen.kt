package com.example.miniradar.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun ExperimentalScreen(navController: NavHostController) {

    val scrollState = rememberLazyListState()
    var switchState by remember { mutableStateOf(true) }

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .background(Color.LightGray.copy(0.2f)),
        elevation = 4.dp,
    ) {

        Column(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                ScreenTitle(scrollState)
                ScreenHeaderSection(scrollState)
            }
            LazyColumn(Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
                item {
                    Text(text = "This is the text")
                }
            }
//              LazyGridView(navController = navController, scrollState = scrollState)
        }

        /*Scaffold(
            topBar = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ScreenTitle(scrollState)
                    ScreenHeaderSection(scrollState)
                }
            },
            content = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    // lazy list
                    if (switchState) {
                        LazyGridView(navController = navController, scrollState = scrollState)
                    } else {
                        LazyColumnView(navController = navController, scrollState = scrollState)
                    }
                }
//            }
            }
        )*/
    }
}

@Preview
@Composable
fun ExperimentalPreview() {
    ExperimentalScreen(rememberNavController())
}
