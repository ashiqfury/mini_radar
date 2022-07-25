package com.example.miniradar

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.miniradar.data.remote.RemotePersonApi
import com.example.miniradar.data.repository.PersonRepository
import com.example.miniradar.navigation.SetupNavGraph
import com.example.miniradar.ui.theme.MiniRadarTheme
import com.example.miniradar.viewmodel.AgentsCardViewModel

class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val remoteHelper = RemotePersonApi(this)
        val repository = PersonRepository(remoteHelper)
        val viewModelFactory = ViewModelFactory(repository)

            //ViewModelProvider( this, viewModelFactory)[AgentsCardViewModel::class.java]

//        viewModel.persons.observeAsState(initial = emptyList())

        setContent {
            MiniRadarTheme {
                val viewModel = viewModel(
                    modelClass = AgentsCardViewModel::class.java,
                    factory = viewModelFactory,
                    key = ""
                )
                navController = rememberNavController()
                SetupNavGraph(navController = navController, viewModel.persons)

                /*Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background),
                ) {

//                    AgentsCardScreen(viewModel.persons)
                    AgentsDetailsScreen()
                }*/
            }
        }
    }
}
//
//fun invokeAgentDetailScreen() {
//    AgentsDetailsScreen()
//}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MiniRadarTheme {
//        AgentsCardScreen()
    }
}