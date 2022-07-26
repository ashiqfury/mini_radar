package com.example.miniradar.screen

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.stopScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.MotionLayout
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import com.example.miniradar.R
import com.example.miniradar.data.model.Person
import com.example.miniradar.navigation.Screen
import kotlinx.coroutines.launch
import java.lang.Float.min
import kotlin.math.log
import kotlin.math.max

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun AgentsCardScreen(navController: NavHostController, personLiveData: LiveData<List<Person>>) {

    val personList by personLiveData.observeAsState(initial = emptyList())

    val scrollState = rememberLazyListState()

    val progress = min(100f,1 + (scrollState.firstVisibleItemScrollOffset / 600f + scrollState.firstVisibleItemIndex) * 10)


    val increaseScrollOffset = max(1f,1 + (scrollState.firstVisibleItemScrollOffset / 600f + scrollState.firstVisibleItemIndex))
    val decreaseScrollOffset = min(1f,1 - (scrollState.firstVisibleItemScrollOffset / 600f + scrollState.firstVisibleItemIndex))

    Log.d("FURY", "AgentsCardScreen: $increaseScrollOffset")

    val titlePadding by animateDpAsState(targetValue = max(0.dp, 40.dp * decreaseScrollOffset))
    val titleOffset by animateDpAsState(targetValue = min(40.dp, 10.dp * 1.5f * decreaseScrollOffset))
    val onlineOffset by animateDpAsState(targetValue = 40.dp * 1.2f * increaseScrollOffset)
    val offlineOffset by animateDpAsState(targetValue = 18.dp * 1.2f * increaseScrollOffset)
    val rowHeight by animateDpAsState(targetValue = min(100.dp, 100.dp + (decreaseScrollOffset.dp * 4.5f)))

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .background(Color.LightGray.copy(0.2f)),
        elevation = 4.dp,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Agents",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(min(110.dp, titleOffset + 110.dp))
                        .padding(vertical = titlePadding)
                        .offset(y = titleOffset),
                    color = MaterialTheme.colors.primary,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    textAlign = TextAlign.Center,
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(rowHeight),
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
                            xOffset = onlineOffset,
                            backgroundColor = Color.Green,
                            isActive = onlineIsActive,
                            size = 100.dp,
                            onClick = {
                                onlineIsActive = !onlineIsActive
                                offlineIsActive = false
                            }
                        )
                        CircularStatCard(
                            count = 15,
                            title = "Offline",
                            textColor = Color.Red,
                            xOffset = offlineOffset,
                            backgroundColor = Color.Red,
                            isActive = offlineIsActive,
                            size = 100.dp,
                            onClick = {
                                onlineIsActive = false
                                offlineIsActive = !offlineIsActive
                            }
                        )
                    }
                    //endregion
                }
            }

            //region LazyVerticalGrid

            val number = (1..50).toList()
            LazyVerticalGrid(
                state = scrollState,
                cells = GridCells.Fixed(3),
                modifier = Modifier
                    .padding(10.dp)
                    .animateContentSize()
            ) {
                val isProfile = true
                items(number.size) { index ->
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
//                                text = personList[index].name.capitalize(LocaleList()),
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
    size: Dp = 100.dp,
    onClick: () -> Unit
) {

    val border = if (isActive) backgroundColor else Color.Transparent
    val bgColor = if (isActive) backgroundColor.copy(0.1f) else Color.LightGray.copy(0.1f)
    val zIndex = if (isActive) 2f else 1f

    Box(
        modifier = Modifier
            .offset(x = xOffset)
            .zIndex(zIndex)
            .size(size)
            .clip(CircleShape)
            .background(bgColor),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .size(size - 15.dp)
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