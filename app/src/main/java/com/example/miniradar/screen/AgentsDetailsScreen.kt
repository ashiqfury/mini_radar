package com.example.miniradar.screen

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.miniradar.data.model.SamplePerson
import com.example.miniradar.navigation.Screen
import com.example.miniradar.ui.theme.BlueCustom
import com.example.miniradar.ui.theme.DarkRedCustom
import com.example.miniradar.ui.theme.LightGreenCustom
import com.example.miniradar.ui.theme.OrangeCustom
import com.example.miniradar.utils.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AgentsDetailsScreen(
    navController: NavHostController,
    personLiveData: LiveData<List<SamplePerson>>,
    personId: Int
) {

    val personList by personLiveData.observeAsState(initial = emptyList())

    val pagerState = rememberPagerState()

    LaunchedEffect(key1 = personId) {
        pagerState.scrollToPage(page = personId)
    }

    /*LaunchedEffect(key1 = Unit) {
        while(true) {
            yield()
            delay(5000)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount),
                animationSpec = tween(600)
            )
        }
    }*/

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
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Search.route) }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }
            )
        }
    ) { padding ->
        HorizontalPager(count = personList.size, state = pagerState) { index ->

            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 0.dp)
                    .background(Color.LightGray.copy(0.2f)),
                elevation = 2.dp,
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
                        if (personList[index].hasProfilePic) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(personList[index].profilePic)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Profile Image",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                                    .graphicsLayer { alpha = 0.99f }
                                    .drawWithContent {
                                        val colors =
                                            listOf(Color.Black, Color.Black, Color.Transparent)
                                        drawContent()
                                        drawRect(
                                            brush = Brush.verticalGradient(colors),
                                            blendMode = BlendMode.DstIn
                                        )
                                    }

                            )
                        } else {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(top = 50.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                ImagePlaceHolder(
                                    text = personList[index].name,
                                    size = 200.dp,
                                    fontSize = MaterialTheme.typography.h5.fontSize,
                                    color = Color.LightGray.copy(0.1f),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = personList[index].name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 50.dp),
                            textAlign = TextAlign.Center,
                            fontSize = MaterialTheme.typography.h4.fontSize,
                            fontWeight = FontWeight.Bold,
                        )

                        Text(
                            text = personList[index].role,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 20.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CanvasSubCircle(
                                count = personList[index].overdue,
                                text = "Overdue",
                                countColor = DarkRedCustom,
                                canvasSize = 90.dp,
                                offset = 10.dp
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            CanvasMainCircle(
                                canvasSize = 120.dp,
                                percentage = personList[index].happinessPercentage
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            CanvasSubCircle(
                                count = personList[index].open,
                                text = "Open",
                                countColor = BlueCustom,
                                canvasSize = 90.dp,
                                offset = 10.dp
                            )
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CanvasSubCircle(
                                count = personList[index].due,
                                text = "Due 1 hr",
                                countColor = DarkRedCustom,
                                canvasSize = 80.dp
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                            CanvasSubCircle(
                                count = personList[index].onHold,
                                text = "On Hold",
                                countColor = OrangeCustom,
                                canvasSize = 80.dp
                            )
                        }
                    }

                    Column {
                        IconSections(
                            title = "Contact",
                            items = listOf(
                                IconItems(Icons.Default.MailOutline, personList[index].email),
                                IconItems(
                                    Icons.Default.Phone,
                                    personList[index].number.repeat(2)
                                ),
                            )
                        )
                        IconSections(
                            title = "Location",
                            items = listOf(
                                IconItems(Icons.Default.LocationOn, personList[index].location),
                                IconItems(Icons.Default.Place, personList[index].language),
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
                            Text(
                                text = "Today",
                                fontSize = MaterialTheme.typography.body2.fontSize
                            )
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
                                    counterColor = BlueCustom
                                )
                            ),
                            canvasType = CanvasLayoutType.DOUBLE
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp))
                                .background(Color.LightGray.copy(0.1f))
                                .padding(15.dp),
                        ) {
                            Text(
                                text = "Last 7 days",
                                fontSize = MaterialTheme.typography.body2.fontSize
                            )
                        }
                        CounterSections(
                            title = "Average Handling Time",
                            items = listOf(
                                CounterItems(
                                    counter = "00:00",
                                    text = "First response",
                                    counterColor = LightGreenCustom
                                ),
                                CounterItems(
                                    counter = "00:00",
                                    text = "Response",
                                    counterColor = MaterialTheme.colors.primary
                                ),
                                CounterItems(
                                    counter = "00:00",
                                    text = "Resolution",
                                    counterColor = BlueCustom
                                ),
                            ),
                            canvasType = CanvasLayoutType.TRIPLE
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
                                    counterColor = BlueCustom
                                ),
                            ),
                            canvasType = CanvasLayoutType.SINGLE
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
            fontSize = MaterialTheme.typography.body2.fontSize,
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
fun CounterSections(
    title: String,
    items: List<CounterItems>,
    canvasType: CanvasLayoutType = CanvasLayoutType.SINGLE
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .weight(1.3f)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.body2.fontSize,
                modifier = Modifier.padding(vertical = 10.dp)
            )
            items.forEach { item ->
                Column(
                    modifier = Modifier.padding(vertical = 10.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = item.counter, color = item.counterColor)
                    Spacer(modifier = Modifier.padding(bottom = 8.dp))
                    Text(text = item.text, fontSize = MaterialTheme.typography.body2.fontSize)
                }
            }
        }
        when (canvasType) {
            CanvasLayoutType.SINGLE -> CanvasSingleCircle()
            CanvasLayoutType.DOUBLE -> CanvasDoubleCircle()
            CanvasLayoutType.TRIPLE -> CanvasTripleCircle()
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

enum class CanvasLayoutType {
    SINGLE, DOUBLE, TRIPLE
}

