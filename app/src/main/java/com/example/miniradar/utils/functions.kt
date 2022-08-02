package com.example.miniradar.utils

fun generateRandomNumber(min: Int = 0, max: Int = 100): Int {
    return (min..max).random()
}