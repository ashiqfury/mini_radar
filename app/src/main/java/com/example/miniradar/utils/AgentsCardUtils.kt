package com.example.miniradar.utils

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.miniradar.R
import com.example.miniradar.data.model.SamplePerson
import com.example.miniradar.navigation.Screen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LazyColumnView(
    navController: NavHostController,
    scrollState: LazyListState,
    personList: List<SamplePerson>
) {
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