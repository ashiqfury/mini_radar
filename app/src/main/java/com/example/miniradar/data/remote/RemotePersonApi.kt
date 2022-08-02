package com.example.miniradar.data.remote

import android.content.Context
import com.example.miniradar.data.model.Person
import com.example.miniradar.data.model.SamplePerson
import com.example.miniradar.utils.generateRandomNumber
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RemotePersonApi(private val context: Context) {

    fun getPersonsData(): List<SamplePerson> {
        val gson = Gson()

        val personType = object : TypeToken<List<SamplePerson>>() {}.type
        val personString = context.assets.open("personsMiniData.json").bufferedReader().use { it.readText() }

        val data =  gson.fromJson(personString, personType) as List<SamplePerson>

        for (index in data.indices) {
            data[index].profilePic = "https://picsum.photos/${index + 200}"
            data[index].happinessPercentage = generateRandomNumber()
            data[index].overdue = generateRandomNumber(0, 30)
            data[index].due = generateRandomNumber(0, 30)
            data[index].onHold = generateRandomNumber(0, 30)
            data[index].open = generateRandomNumber(0, 30)
        }

        return data
    }
}