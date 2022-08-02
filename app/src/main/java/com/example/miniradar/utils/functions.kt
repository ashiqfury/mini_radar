package com.example.miniradar.utils

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