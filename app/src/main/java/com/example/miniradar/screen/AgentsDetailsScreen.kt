package com.example.miniradar.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.unit.dp
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

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Agents", color = MaterialTheme.colors.primary) },
                backgroundColor = Color.White,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Localized description",
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }
            )
        },
        content = {
            HorizontalPager(count = items.size, state = pagerState) { currentPage ->

                Card(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
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
                                painter = painterResource(id = R.drawable.image),
                                contentDescription = "Profile Picture",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                                    .graphicsLayer { alpha = 0.99F }
                                    .drawWithContent {
                                        val colors =
                                            listOf(Color.Black, Color.Black, Color.Transparent)
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

                            // Departments
                            DepartmentSection()

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
    )


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

@Composable
fun DepartmentSection(textArray: List<String> = emptyList()) {
    val array = listOf(
        "Hardware edit",
        "pooja-Happiness",
        "Hardware edit",
        "pooja-Happinessssss",
        "Test555",
        "Test new department",
        "test789",
        "newtesting",
        "New department2",
        "depart11",
        "Testing 12",
        "test dep111",
        "test new 86876757657655575765 சோதனைசோதனை",
        "test55555555i66876சோதனை",
        "Basheer testing",
        "UI Process",
        "New department banu",
        "Department testing",
        "Chan Dept",
        "test சோதனை",
        "new department - ks",
        "New department - Vignesh",
        "Permission check",
        "Rathish Marthandan - Ticket Activity",
        "AravindTesting",
        "testing deparmtne",
        "pooja-test",
        "articles",
        "विभाग हिंदी",
        "Department English check",
        "Test new department check",
        "Test department 23234234234",
        "pooja-layout",
        "Banu QATesting",
        "Testing Feed",
        "Layout Testing",
        "new department check",
        "Arul_test",
        "sdsdsdsdsdsdsds",
        "Pooja-Market place",
        "new de45",
        "ty67777",
        "last dep3",
        "test de67",
        "new department com",
        "Tata Communications",
        "New 1234",
        "New check topic",
        "Again check topic",
        "New 13232323",
        "screenrecDept",
        "13dep",
        "New department check today",
        "12Dep",
        "issue dep",
        "dep90",
        "sdga",
        "testAddTopic2",
        "testAddTopic3",
        "testDepAddTopic",
        "Check department name",
        "tttttt deeee",
        "check department111",
        "4",
        "banu test12",
        "dep l",
        "67898786786876",
        "Error Department",
        "Test new department Banu",
        "Test NewToday14",
        "New Testing qa11",
        "Testing Tamil",
        "New Department KB - check",
        "TestingPortalPrivate"
    )
    val string = array.joinToString(", ")
    var visibility by remember { mutableStateOf(false) }
    val maxLines = if (visibility) Int.MAX_VALUE else 2

    Column(modifier = Modifier.padding(horizontal = 30.dp)) {
        Text(
            text = "Departments",
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.body1.fontSize,
            modifier = Modifier.padding(vertical = 10.dp)
        )

        Row(
            modifier = Modifier.padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.List,
                contentDescription = null,
                tint = Color.LightGray,
                modifier = Modifier.size(27.dp)
            )
            Spacer(modifier = Modifier.padding(end = 20.dp))
            Column {
                Text(text = string, maxLines = maxLines)
                if (!visibility) {
                    Text(
                        text = "Show all",
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.clickable {
                            visibility = !visibility
                        },
                    )
                }
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