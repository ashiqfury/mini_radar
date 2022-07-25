package com.example.miniradar.data.remote

import android.content.Context
import com.example.miniradar.data.model.Person
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RemotePersonApi(private val context: Context) {

    fun getPersonsData(): List<Person> {
        val gson = Gson()

        val personType = object : TypeToken<List<Person>>() {}.type
        val personString = context.assets.open("personsData.json").bufferedReader().use { it.readText() }

        return gson.fromJson(personString, personType)
    }
}