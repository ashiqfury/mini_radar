package com.example.miniradar.utils

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// counter column section
@Composable
fun RowScope.CanvasTripleCircle() {
    val canvasSize = 150.dp
    Box(
        modifier = Modifier
            .size(canvasSize)
            .weight(0.7f)
            .padding(end = 30.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val stroke = 25f

            drawCircle(
                color = Color.Cyan.copy(0.1f),
                center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                radius = size.minDimension / 2,
                style = Stroke(width = stroke)
            )
            drawCircle(
                color = Color.Cyan.copy(0.1f),
                center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                radius = size.minDimension / 3,
                style = Stroke(width = stroke)
            )
            drawCircle(
                color = Color.Green.copy(0.1f),
                center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                radius = size.minDimension / 6,
                style = Stroke(width = stroke)
            )
        }
        Text(
            text = "Hrs",
            fontSize = MaterialTheme.typography.body2.fontSize,
            color = Color.DarkGray.copy(0.5f)
        )
    }
}

@Composable
fun RowScope.CanvasDoubleCircle() {
    val canvasSize = 150.dp
    Box(
        modifier = Modifier
            .size(canvasSize)
            .weight(0.7f)
            .padding(end = 30.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val stroke = 25f
            drawCircle(
                color = Color.Cyan.copy(0.1f),
                center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                radius = size.minDimension / 3,
                style = Stroke(width = stroke)
            )
            drawCircle(
                color = Color.Green.copy(0.1f),
                center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                radius = size.minDimension / 6,
                style = Stroke(width = stroke)
            )
        }
    }
}

@Composable
fun RowScope.CanvasSingleCircle() {
    val canvasSize = 150.dp
    Box(
        modifier = Modifier
            .size(canvasSize)
            .weight(0.7f)
            .padding(end = 30.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val stroke = 25f
            drawCircle(
                color = Color.Cyan.copy(0.1f),
                center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                radius = size.minDimension / 3,
                style = Stroke(width = stroke)
            )
        }
        Text(
            text = "0%",
            fontSize = MaterialTheme.typography.body2.fontSize,
            color = Color.DarkGray.copy(0.5f)
        )
    }
}


// main stats section
@Composable
fun RowScope.CanvasMainCircle(canvasSize: Dp = 150.dp) {
    Box(
        modifier = Modifier
            .size(canvasSize),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val stroke = 25f
            drawCircle(
                color = Color.LightGray.copy(0.1f),
                center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                radius = size.minDimension / 2,
                style = Stroke(width = 7f)
            )
            drawCircle(
                color = Color.LightGray.copy(0.25f),
                center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                radius = size.minDimension / 2.5f,
                style = Stroke(width = 10f)
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "0%",
                fontSize = MaterialTheme.typography.body2.fontSize,
                color = Color.DarkGray.copy(0.8f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Happiness \n rating",
                fontSize = MaterialTheme.typography.caption.fontSize,
                color = Color.DarkGray.copy(0.5f),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun RowScope.CanvasSubCircle(
    canvasSize: Dp = 150.dp,
    count: Int,
    text: String,
    countColor: Color,
    offset: Dp = 0.dp
) {
    Box(
        modifier = Modifier
            .offset(y = offset)
            .size(canvasSize),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val stroke = 25f
            drawCircle(
                color = Color.LightGray.copy(0.1f),
                center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                radius = size.minDimension / 2,
                style = Stroke(width = 7f)
            )

        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$count",
                fontSize = MaterialTheme.typography.body2.fontSize,
                color = countColor.copy(0.8f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                fontSize = MaterialTheme.typography.caption.fontSize,
                color = Color.DarkGray.copy(0.5f),
                textAlign = TextAlign.Center,

            )
        }
    }
}