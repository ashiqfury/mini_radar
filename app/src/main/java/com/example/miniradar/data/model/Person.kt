package com.example.miniradar.data.model

data class Person(
    val id: Int,
    val name: String,
    val role: String, // i.e CEO - Support Administrator
    val contact: Contact,
    val location: Location,
    val stats: Stats,
    val departments: List<String>,
    val response: Response,
    val time: Time,
    val resolution: Resolution,
    val status: String = "offline", // shows online or offline
    val hasProfilePic: Boolean = false, // have profile picture or not
)