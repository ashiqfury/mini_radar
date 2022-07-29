package com.example.miniradar.data.remote

import android.content.Context
import com.example.miniradar.data.model.Person
import com.example.miniradar.data.model.SamplePerson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RemotePersonApi(private val context: Context) {

    fun getPersonsData(): List<SamplePerson> {
        val gson = Gson()

        val personType = object : TypeToken<List<SamplePerson>>() {}.type
        val personString = context.assets.open("personsMiniData.json").bufferedReader().use { it.readText() }

        return gson.fromJson(personString, personType)
    }
}