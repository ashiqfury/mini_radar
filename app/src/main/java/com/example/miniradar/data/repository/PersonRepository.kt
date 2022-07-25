package com.example.miniradar.data.repository

import com.example.miniradar.data.model.Person
import com.example.miniradar.data.remote.RemotePersonApi

class PersonRepository(private val remoteHelper: RemotePersonApi) {

    private suspend fun getDataFromNetwork(): List<Person> {

        return remoteHelper.getPersonsData()
    }

    suspend fun getAllPersonsData(): List<Person> {
        return getDataFromNetwork()
    }
}