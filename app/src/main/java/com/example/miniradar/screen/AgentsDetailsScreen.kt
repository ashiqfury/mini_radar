package com.example.miniradar.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.example.miniradar.R

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AgentsDetailsScreen(navController: NavHostController) {
    val pagerState = rememberPagerState()
    val items = (0..10).toList()
    HorizontalPager(count = items.size, state = pagerState) { currentPage ->

        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .background(Color.LightGray.copy(0.2f)),
            elevation = 4.dp,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.user_avatar),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .graphicsLayer { alpha = 0.99F }
                            .drawWithContent {
                                val colors = listOf(Color.Black, Color.Black, Color.Transparent)
                                drawContent()
                                drawRect(
                                    brush = Brush.verticalGradient(colors),
                                    blendMode = BlendMode.DstIn
                                )
                            },
                        contentScale = ContentScale.Fit
                    )

                    Text(
                        text = "Ashiq",
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 50.dp),
                        textAlign = TextAlign.Center,
                        fontSize = MaterialTheme.typography.h4.fontSize,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        text = "CEO - Software Developer",
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 20.dp),
                        textAlign = TextAlign.Center
                    )
                }

                Column {
                    IconSections(
                        title = "Contact",
                        items = listOf(
                            IconItems(Icons.Default.MailOutline, "ashiqasraf07@gmail.com"),
                            IconItems(Icons.Default.Phone, "55552"),
                        )
                    )
                    IconSections(
                        title = "Location",
                        items = listOf(
                            IconItems(Icons.Default.LocationOn, "India"),
                            IconItems(Icons.Default.Place, "English"),
                        )
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp))
                            .background(Color.LightGray.copy(0.1f))
                            .padding(15.dp),
                    ) {
                        Text(text = "Today")
                    }
                    CounterSections(
                        title = "Hourly responses",
                        items = listOf(
                            CounterItems(
                                counter = "0",
                                text = "Incoming",
                                counterColor = MaterialTheme.colors.primary
                            ),
                            CounterItems(
                                counter = "0",
                                text = "Outgoing",
                                counterColor = MaterialTheme.colors.primary
                            )
                        )
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp))
                            .background(Color.LightGray.copy(0.1f))
                            .padding(15.dp),
                    ) {
                        Text(text = "Last 7 days")
                    }
                    CounterSections(
                        title = "Average Handling Time",
                        items = listOf(
                            CounterItems(
                                counter = "00:00",
                                text = "First response",
                                counterColor = Color.Cyan
                            ),
                            CounterItems(
                                counter = "00:00",
                                text = "Response",
                                counterColor = MaterialTheme.colors.primary
                            ),
                            CounterItems(
                                counter = "00:00",
                                text = "Resolution",
                                counterColor = Color.Blue
                            ),
                        )
                    )
                    CounterSections(
                        title = "First Contact Resolution",
                        items = listOf(
                            CounterItems(
                                counter = "0",
                                text = "Total Closed Tickets",
                                counterColor = MaterialTheme.colors.primary
                            ),
                            CounterItems(
                                counter = "0",
                                text = "FCR Closed Tickets",
                                counterColor = Color.Blue
                            ),
                        )
                    )
                }
            }
        }

        /*val coroutineScope = rememberCoroutineScope()
        Box(
            modifier = Modifier.fillMaxSize().padding(40.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(page = 2)
                    }
                }
            ) {
                Text(text = "Scroll to the third page")
            }
        }*/
    }
}

@Composable
fun IconSections(
    title: String,
    items: List<IconItems>
) {
    Column(modifier = Modifier.padding(horizontal = 30.dp)) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.body1.fontSize,
            modifier = Modifier.padding(vertical = 10.dp)
        )
        items.forEach { item ->
            Row(
                modifier = Modifier.padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = title,
                    tint = Color.LightGray,
                    modifier = Modifier.size(27.dp)
                )
                Spacer(modifier = Modifier.padding(end = 20.dp))
                Text(text = item.text)
            }
        }
    }
    Divider(
        color = Color.LightGray.copy(0.2f),
        modifier = Modifier.padding(vertical = 10.dp)
    )
}

@Composable
fun CounterSections(title: String, items: List<CounterItems>) {
    Column(modifier = Modifier.padding(horizontal = 30.dp)) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.body1.fontSize,
            modifier = Modifier.padding(vertical = 10.dp)
        )
        items.forEach { item ->
            Column(
                modifier = Modifier.padding(vertical = 10.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = item.counter, color = item.counterColor)
                Spacer(modifier = Modifier.padding(bottom = 10.dp))
                Text(text = item.text)
            }
        }
    }
    Divider(
        color = Color.LightGray.copy(0.2f),
        modifier = Modifier.padding(vertical = 10.dp)
    )
}

data class IconItems(
    val icon: ImageVector,
    val text: String,
)

data class CounterItems(
    val counter: String,
    val text: String,
    val counterColor: Color
)