package com.example.miniradar.screen

import android.util.Log
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import com.example.miniradar.data.model.SamplePerson
import com.example.miniradar.navigation.Screen
import com.example.miniradar.ui.theme.TestColor
import java.util.*
import kotlin.collections.ArrayList

@Composable
fun AgentsSearchScreen(navController: NavHostController, personLiveData: LiveData<List<SamplePerson>>) {

    val personList by personLiveData.observeAsState(initial = emptyList())

    val searchedList: ArrayList<SamplePerson> = arrayListOf()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(0.2f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(60.dp)
                .shadow(elevation = 1.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
            var text by rememberSaveable { mutableStateOf("") }

            if (text.isEmpty()) {
                searchedList.clear()
                searchedList.addAll(personList)
            } else {
                searchedList.clear()
                personList.forEach {  person ->
                    if (person.name.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))) {
                        searchedList.add(person)
                    }
                }
            }

            BasicTextField(modifier = Modifier
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
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                Modifier.size(20.dp),
                                tint = Color.Black.copy(0.4f)
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
                                Modifier.size(20.dp),
                                tint = Color.Black.copy(0.4f)
                            )
                        }
                    }
                }
            )
        }
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(searchedList.size - 1) { index ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.Detail.withArgs(personList[index].id - 1)) {
                                popUpTo(Screen.Home.route) {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                        }
                        .padding(vertical = 10.dp, horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (personList[index].hasProfilePic) {
                        CoilImage(url = personList[index].profilePic, size = 60.dp)
                    } else {
                        ImagePlaceHolder(text = personList[index].name, size = 60.dp)
                    }
                    Column(
                    modifier = Modifier.padding(start = 10.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(text = personList[index].name)
                        Text(text = personList[index].email)
                    }
                }
            }
        }
    }
}