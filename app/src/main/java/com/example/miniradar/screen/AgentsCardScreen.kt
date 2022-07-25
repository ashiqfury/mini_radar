package com.example.miniradar.screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import com.example.miniradar.R
import com.example.miniradar.data.model.Person
import com.example.miniradar.navigation.Screen

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun AgentsCardScreen(navController: NavHostController, personLiveData: LiveData<List<Person>>) {

    val personList by personLiveData.observeAsState(initial = emptyList())

    Log.d("FURY", "AgentsCardScreen: $personList")

    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .background(Color.LightGray.copy(0.2f)),
        elevation = 4.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Agents",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp),
                color = MaterialTheme.colors.primary,
                fontSize = MaterialTheme.typography.h5.fontSize,
                textAlign = TextAlign.Center
            )
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp))
                        .background(Color.LightGray.copy(0.1f))
                        .padding(15.dp),
                ) {
                    Text(text = "All Agents")
                }
                //region circular card
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    var onlineIsActive by remember { mutableStateOf(false) }
                    var offlineIsActive by remember { mutableStateOf(false) }
                    CircularStatCard(
                        count = 2,
                        title = "Online",
                        textColor = MaterialTheme.colors.primary,
                        xOffset = 40.dp,
                        backgroundColor = Color.Green,
                        isActive = onlineIsActive,
                        onClick = {
                            onlineIsActive = !onlineIsActive
                            offlineIsActive = false
                        }
                    )
                    CircularStatCard(
                        count = 15,
                        title = "Offline",
                        textColor = Color.Red,
                        xOffset = 20.dp,
                        backgroundColor = Color.Red,
                        isActive = offlineIsActive,
                        onClick = {
                            onlineIsActive = false
                            offlineIsActive = !offlineIsActive
                        }
                    )
                }
                //endregion
            }

            //region LazyVerticalGrid

            val number = (1..50).toList()
            LazyVerticalGrid(
                cells = GridCells.Fixed(3),
                modifier = Modifier.padding(10.dp)
            ) {
                val isProfile = true
                items(number.size) {
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(3.dp),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            navController.navigate(Screen.Detail.route)
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.LightGray.copy(0.1f))
                                .padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            if (isProfile) {
                                Image(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(CircleShape),
                                    painter = painterResource(R.drawable.user_avatar),
                                    contentDescription = "Image",
                                    contentScale = ContentScale.FillBounds,
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(80.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "AB")
                                }
                            }

                            Text(
                                text = "Ashiq",
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .padding(5.dp),
                                textAlign = TextAlign.Center,
                                fontSize = MaterialTheme.typography.caption.fontSize
                            )
                        }
                    }
                }
            }
            //endregion
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CircularStatCard(
    count: Int = 0,
    title: String = "Online",
    textColor: Color = MaterialTheme.colors.primary,
    xOffset: Dp = 0.dp,
    backgroundColor: Color = Color.Green,
    isActive: Boolean = false,
    onClick: () -> Unit
) {

    val border = if (isActive) backgroundColor else Color.Transparent
    val bgColor = if (isActive) backgroundColor.copy(0.1f) else Color.LightGray.copy(0.1f)
    val zIndex = if (isActive) 2f else 1f

    Box(
        modifier = Modifier
            .offset(x = xOffset)
            .zIndex(zIndex)
            .size(100.dp)
            .clip(CircleShape)
            .background(bgColor),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .size(85.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(1.dp, border, CircleShape),
            shape = RoundedCornerShape(500.dp),
            onClick = onClick
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(text = count.toString(), color = textColor)
                Text(text = title)
            }
        }
    }
}


@Preview
@Composable
fun AgentsCardScreenPreview() {
//    AgentComposition()
}