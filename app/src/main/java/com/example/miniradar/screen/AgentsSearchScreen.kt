package com.example.miniradar.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import com.example.miniradar.data.model.SamplePerson
import com.example.miniradar.navigation.Screen

@Composable
fun AgentsSearchScreen(
    navController: NavHostController,
    personLiveData: LiveData<List<SamplePerson>>
) {

    val personList by personLiveData.observeAsState(initial = emptyList())

    var text by rememberSaveable { mutableStateOf("") }
    val searchedList = remember { mutableStateListOf<SamplePerson>() }

    LaunchedEffect(key1 = text) {
        if (text.isNotEmpty()) {
            searchedList.clear()
            personList.filter { person ->
                person.name.startsWith(text, true)
            }.also { searchedList.addAll(it) }
        } else {
            searchedList.also {
                it.clear()
                it.addAll(personList)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(0.1f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(55.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                modifier = Modifier.padding(start = 5.dp),
                onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    Modifier.size(24.dp),
                    tint = Color.Black.copy(0.8f),
                )
            }
            BasicTextField(modifier = Modifier
                .padding(vertical = 10.dp)
                .background(
                    MaterialTheme.colors.surface,
                    MaterialTheme.shapes.small,
                )
                .fillMaxWidth()
                .clip(RoundedCornerShape(50.dp)),
                value = text,
                onValueChange = {
                    text = it
                },
                singleLine = true,
                cursorBrush = SolidColor(MaterialTheme.colors.primary),
                textStyle = LocalTextStyle.current.copy(
                    color = MaterialTheme.colors.onSurface,
                    fontSize = MaterialTheme.typography.body2.fontSize
                ),
                decorationBox = { innerTextField ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray.copy(0.2f)),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(onClick = { }, enabled = false) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                Modifier.size(18.dp),
                                tint = Color.Black.copy(0.3f)
                            )
                        }
                        Box(Modifier.weight(1f)) {
                            if (text.isEmpty()) Text(
                                "Search",
                                style = LocalTextStyle.current.copy(
                                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                                    fontSize = MaterialTheme.typography.body2.fontSize
                                )
                            )
                            innerTextField()
                        }
                        IconButton(onClick = { text = "" }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                Modifier.size(18.dp),
                                tint = Color.Black.copy(0.3f)
                            )
                        }
                    }
                }
            )
        }
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(
                count = searchedList.size,
                key = { index ->
                    searchedList[index].id
                },

                ) { index ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.Detail.withArgs(searchedList[index].id - 1)) {
                                popUpTo(Screen.Home.route) {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                        }
                        .padding(vertical = 10.dp, horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (searchedList[index].hasProfilePic) {
                        CoilImage(url = searchedList[index].profilePic, size = 60.dp)
                    } else {
                        ImagePlaceHolder(text = searchedList[index].name, size = 60.dp)
                    }
                    Column(
                        modifier = Modifier.padding(start = 20.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = searchedList[index].name,
                            fontSize = MaterialTheme.typography.body1.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = searchedList[index].email,
                            fontSize = MaterialTheme.typography.caption.fontSize,
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }
    }
}