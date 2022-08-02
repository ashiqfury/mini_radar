package com.example.miniradar.data.model

data class SamplePerson(
    val id: Int,
    val name: String,
    val role: String,
    val email: String,
    val number: String,
    val language: String,
    val location: String,
    val isOnline: Boolean,
    var profilePic: String,
    var happinessPercentage: Int,
    var overdue: Int,
    var due: Int,
    var onHold: Int,
    var open: Int
)
