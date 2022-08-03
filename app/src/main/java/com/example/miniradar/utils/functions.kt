package com.example.miniradar.utils

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

fun generateRandomNumber(min: Int = 0, max: Int = 100): Int {
    return (min..max).random()
}

fun calculateInitialFromName(text: String): String {
    var name = ""
    val textArray = text.split(" ")
    if (textArray.size == 1) {
        name += textArray[0][0]
        name += textArray[0][1]
    } else if (textArray.size > 1) {
        name += textArray[0][0]
        name += textArray[1][0]
    }
    return name
}

object ClearRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor(): Color = Color.Transparent

    @Composable
    override fun rippleAlpha() = RippleAlpha(
        draggedAlpha = 0.0f,
        focusedAlpha = 0.0f,
        hoveredAlpha = 0.0f,
        pressedAlpha = 0.0f,
    )
}