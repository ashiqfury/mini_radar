package com.example.miniradar.data.repository

import com.example.miniradar.data.model.Person
import com.example.miniradar.data.model.SamplePerson
import com.example.miniradar.data.remote.RemotePersonApi

class PersonRepository(private val remoteHelper: RemotePersonApi) {

    private suspend fun getDataFromNetwork(): List<SamplePerson> {

        return remoteHelper.getPersonsData()
    }

    suspend fun getAllPersonsData(): List<SamplePerson> {
        return getDataFromNetwork()
    }
}