package com.example.miniradar.data.model

import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
data class Time(
    val firstResponse: String = "00:00",
    val responses: String = "00:00",
    val resolutions: String = "00:00",
)