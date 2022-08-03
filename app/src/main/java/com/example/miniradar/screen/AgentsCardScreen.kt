package com.example.miniradar.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.zIndex
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.miniradar.R
import com.example.miniradar.data.model.SamplePerson
import com.example.miniradar.navigation.Screen
import com.example.miniradar.ui.theme.GreenCustom
import com.example.miniradar.ui.theme.TestColor
import com.example.miniradar.utils.ClearRippleTheme
import com.example.miniradar.utils.LazyColumnView
import com.example.miniradar.utils.calculateInitialFromName
import java.lang.Float.min
import kotlin.math.max

@Composable
fun AgentsCardScreen(
    navController: NavHostController,
    personLiveData: LiveData<List<SamplePerson>>
) {

    val personList by personLiveData.observeAsState(initial = emptyList())
    val scrollState = rememberLazyListState()
    /*SharedMaterialContainer(
        key = personList[0].name,
        screenKey = Screen.Home.route,
        shape = MaterialTheme.shapes.medium,
        elevation = 2.dp,
        transitionSpec = MaterialContainerTransformSpec(
            pathMotionFactory = MaterialArcMotionFactory,
            durationMillis = 1000,
            fadeMode = FadeMode.Out
        )
    ) {}*/
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .background(Color.LightGray.copy(0.2f), shape = RoundedCornerShape(10.dp)),
        elevation = 2.dp,
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
            content = {
                LazyGridView(
                    navController = navController,
                    scrollState = scrollState,
                    personList = personList
                )
            }
        )
    }
}


@Composable
fun ScreenTitle(scrollState: LazyListState) {
    val decreaseScrollOffset = getDecreasedScrollOffset(scrollState = scrollState)
    val titleOffset by animateDpAsState(
        targetValue = min(
            40.dp,
            10.dp * 1.5f * decreaseScrollOffset
        )
    )

    val titleHeight by animateDpAsState(targetValue = 40.dp * decreaseScrollOffset)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(min(115.dp, titleHeight + 55.dp))
            .offset(y = titleOffset),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Agents",
            modifier = Modifier
                .fillMaxWidth(),
            color = MaterialTheme.colors.primary,
            fontSize = MaterialTheme.typography.h5.fontSize,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun ScreenHeaderSection(scrollState: LazyListState) {

    val decreaseScrollOffset = getDecreasedScrollOffset(scrollState = scrollState)
    val increasedScrollOffset = getIncreasedScrollOffset(scrollState = scrollState)
    val rowHeight by animateDpAsState(
        min(
            90.dp,
            100.dp + (decreaseScrollOffset.dp * 4.5f)
        )
    )
    val bgColor = if ((decreaseScrollOffset + 0.5) > 0) min(decreaseScrollOffset + 1f, 1f) else 0f
    val countOffset: Dp by animateDpAsState(min(60.dp + (increasedScrollOffset.dp * 15), 110.dp))
    Row(
        Modifier
            .fillMaxWidth()
            .height(rowHeight),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .width(150.dp)
                .offset(x = (countOffset - 110.dp) * 0.5f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "15",
                color = GreenCustom,
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.typography.body2.fontSize,
                modifier = Modifier
                    .offset(x = countOffset)
                    .zIndex(-2f)
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp))
//                    .background(color = backColor)
                    .background(color = TestColor.copy(bgColor))
                    .padding(15.dp),
            ) {
                Text(
                    text = "All Agents",
                    color = Color.DarkGray,
                    fontSize = MaterialTheme.typography.body2.fontSize
                )
            }
        }
        //region circular card
        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            var onlineIsActive by remember { mutableStateOf(false) }
            var offlineIsActive by remember { mutableStateOf(false) }
            CompositionLocalProvider(
                LocalRippleTheme provides ClearRippleTheme
            ) {
                CircularStatCard(
                    count = 2,
                    title = "Online",
                    textColor = MaterialTheme.colors.primary,
                    scrollState = scrollState,
                    backgroundColor = Color.Green,
                    isActive = onlineIsActive,
                    size = 90.dp,
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
                    size = 90.dp,
                    onClick = {
                        onlineIsActive = false
                        offlineIsActive = !offlineIsActive
                    }
                )
            }
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

    val borderColor by animateColorAsState(
        targetValue = if (isActive) backgroundColor.copy(0.5f) else TestColor,
        tween(durationMillis = 300)
    )
    val bgColor by animateColorAsState(
        targetValue = if (isActive) backgroundColor.copy(0.1f) else TestColor,
        tween(durationMillis = 300)
    )
    val zIndex by animateFloatAsState(
        targetValue = if (isActive) 2f else 1f,
        tween(durationMillis = 300)
    )

    val increaseScrollOffset = getIncreasedScrollOffset(scrollState = scrollState)
    val offset by animateDpAsState(
        targetValue = if (title == "Online") {
            val onlineOffset by animateDpAsState(targetValue = 40.dp * 1.2f * increaseScrollOffset)
            onlineOffset
        } else {
            val offlineOffset by animateDpAsState(targetValue = 18.dp * 1.2f * increaseScrollOffset)
            offlineOffset
        }
    )

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
                .size(size - 12.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(1.dp, borderColor, CircleShape),
            shape = RoundedCornerShape(500.dp),
            onClick = onClick
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = count.toString(),
                    color = textColor,
                    fontSize = MaterialTheme.typography.body2.fontSize
                )
                Text(
                    text = title,
                    color = Color.DarkGray.copy(0.8f),
                    fontSize = MaterialTheme.typography.caption.fontSize
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun LazyGridView(
    navController: NavHostController,
    scrollState: LazyListState,
    personList: List<SamplePerson>
) {

    val columnsCount = 3
    LazyVerticalGrid(
        state = scrollState,
        cells = GridCells.Fixed(columnsCount),
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp, top = 0.dp)
            .animateContentSize()
            .fillMaxWidth()
    ) {
        items(count = personList.size) { index ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .padding(2.dp),
                shape = RoundedCornerShape(10.dp),
                elevation = 0.dp,
                onClick = {
                    navController.navigate(Screen.Detail.withArgs(personList[index].id - 1))
                },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray.copy(0.1f))
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (personList[index].hasProfilePic) {
                        CoilImage(url = personList[index].profilePic, size = 80.dp)
                    } else {
                        ImagePlaceHolder(text = personList[index].name)
                    }
                    Text(
                        text = personList[index].name,
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



const val SCROLL_CONST = 600f
fun getIncreasedScrollOffset(scrollState: LazyListState): Float {
    return max(
        1f,
        1 + (scrollState.firstVisibleItemScrollOffset / SCROLL_CONST + scrollState.firstVisibleItemIndex)
    )
}

fun getDecreasedScrollOffset(scrollState: LazyListState): Float {
    return min(
        1f,
        1 - (scrollState.firstVisibleItemScrollOffset / SCROLL_CONST + scrollState.firstVisibleItemIndex)
    )
}

/*fun getProgress(scrollState: LazyListState): Float {
    return min(
        100f,
        1 + (scrollState.firstVisibleItemScrollOffset / SCROLL_CONST + scrollState.firstVisibleItemIndex) * 20
    )
}*/


@Composable
fun ImagePlaceHolder(
    text: String,
    size: Dp = 80.dp,
    color: Color = Color.LightGray.copy(0.2f),
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Box(
        modifier = Modifier
            .clip(shape = CircleShape)
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color.White,
                        Color.White,
                        Color.White,
                        color
                    )
                )
            )
            .size(size),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = calculateInitialFromName(text).uppercase(),
            fontSize = fontSize,
            fontWeight = fontWeight
        )
    }
}

@Composable
fun CoilImage(url: String, size: Dp = 80.dp) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = "Profile Image",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
    )
}

@Preview
@Composable
fun AgentsCardScreenPreview() {

}