package com.example.miniradar.screen

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
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
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import androidx.compose.ui.zIndex
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.miniradar.R
import com.example.miniradar.data.model.SamplePerson
import com.example.miniradar.navigation.Screen
import java.lang.Float.min
import kotlin.math.max

@Composable
fun AgentsCardScreen(navController: NavHostController, personLiveData: LiveData<List<SamplePerson>>) {

    val personList by personLiveData.observeAsState(initial = emptyList())

    val scrollState = rememberLazyListState()
    val progress = min(100f,1 + (scrollState.firstVisibleItemScrollOffset / 600f + scrollState.firstVisibleItemIndex) * 20)

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .background(Color.LightGray.copy(0.2f)),
        elevation = 4.dp,
    ) {
        Scaffold(
            topBar = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ScreenTitle(scrollState)
                    ScreenHeaderSection(scrollState)
                }
            },
            content = { padding ->
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    // lazy list
                    val switchState by remember { mutableStateOf(true) }
                    if (switchState) {
                        LazyGridView(navController = navController, scrollState = scrollState, personList = personList)
                    } else {
                        LazyColumnView(navController = navController, scrollState = scrollState, personList = personList)
                    }
                }
            }
        )
    }
}


@Composable
fun ScreenTitle(scrollState: LazyListState) {
    val decreaseScrollOffset = min(1f,1 - (scrollState.firstVisibleItemScrollOffset / 600f + scrollState.firstVisibleItemIndex))
    val titlePadding by animateDpAsState(targetValue = max(0.dp, 40.dp * decreaseScrollOffset))
    val titleOffset by animateDpAsState(targetValue = min(40.dp, 10.dp * 1.5f * decreaseScrollOffset))
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
}

@Composable
fun ScreenHeaderSection(scrollState: LazyListState) {

    val decreaseScrollOffset = min(1f, 1 - (scrollState.firstVisibleItemScrollOffset / 600f + scrollState.firstVisibleItemIndex))
    val rowHeight by animateDpAsState(targetValue = min(100.dp, 100.dp + (decreaseScrollOffset.dp * 4.5f)))
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
                scrollState = scrollState,
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
                scrollState = scrollState,
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CircularStatCard(
    count: Int = 0,
    title: String = "Online",
    textColor: Color = MaterialTheme.colors.primary,
    backgroundColor: Color = Color.Green,
    isActive: Boolean = false,
    size: Dp = 100.dp,
    scrollState: LazyListState,
    onClick: () -> Unit
) {

    val border = if (isActive) backgroundColor else Color.Transparent
    val bgColor = if (isActive) backgroundColor.copy(0.1f) else Color.LightGray.copy(0.1f)
    val zIndex = if (isActive) 2f else 1f

    val increaseScrollOffset = max(
        1f,
        1 + (scrollState.firstVisibleItemScrollOffset / 600f + scrollState.firstVisibleItemIndex)
    )
    val offset: Dp = if (title == "Online") {
        val onlineOffset by animateDpAsState(targetValue = 40.dp * 1.2f * increaseScrollOffset)
        onlineOffset
    } else {
        val offlineOffset by animateDpAsState(targetValue = 18.dp * 1.2f * increaseScrollOffset)
        offlineOffset
    }

    Box(
        modifier = Modifier
            .offset(x = offset)
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

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun LazyGridView(navController: NavHostController, scrollState: LazyListState, personList: List<SamplePerson>) {

    val columnsCount = 3
    LazyVerticalGrid(
        state = scrollState,
        cells = GridCells.Fixed(columnsCount),
        modifier = Modifier
            .padding(10.dp)
            .animateContentSize()
            .fillMaxWidth()
    ) {
        val isProfile = true

        items(count = personList.size) { index ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .padding(2.dp),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    navController.navigate(Screen.Detail.withArgs(index))
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
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
                        text = personList[index].name,
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
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LazyColumnView(navController: NavHostController, scrollState: LazyListState, personList: List<SamplePerson>) {
    LazyColumn(
        state = scrollState,
        modifier = Modifier
            .padding(10.dp)
            .animateContentSize()
    ) {
        val isProfile = true
        items(personList.size) { index ->
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(3.dp),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    navController.navigate(Screen.Detail.withArgs(index))
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray.copy(0.1f))
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
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
                        text = personList[index].name,
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
}


@Preview
@Composable
fun AgentsCardScreenPreview() {
    ExperimentalScreen(navController = rememberNavController())
}