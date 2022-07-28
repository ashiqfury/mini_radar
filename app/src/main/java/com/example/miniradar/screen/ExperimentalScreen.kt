package com.example.miniradar.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun ExperimentalScreen(navController: NavHostController) {

    Box(
        modifier = Modifier.size(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            drawCircle(
                color = Color.Cyan.copy(0.05f),
                center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                radius = size.minDimension / 4,
                style = Stroke(width = 30f)
            )
            drawCircle(
                color = Color.Cyan.copy(0.05f),
                center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                radius = size.minDimension / 5,
                style = Stroke(width = 30f)
            )
            drawCircle(
                color = Color.Green.copy(0.05f),
                center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                radius = size.minDimension / 7,
                style = Stroke(width = 30f)
            )
        }
        Text(
            text = "Hrs",
            fontSize = MaterialTheme.typography.h6.fontSize,
            color = Color.DarkGray
        )
    }

}


@Preview
@Composable
fun ExperimentalPreview() {
    ExperimentalScreen(rememberNavController())
}
