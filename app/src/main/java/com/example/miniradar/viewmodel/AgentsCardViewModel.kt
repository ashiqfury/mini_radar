package com.example.miniradar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniradar.data.model.Person
import com.example.miniradar.data.repository.PersonRepository
import kotlinx.coroutines.launch

class AgentsCardViewModel(repository: PersonRepository) : ViewModel() {

    private var _persons: MutableLiveData<List<Person>> = MutableLiveData()
    val persons: LiveData<List<Person>> = _persons

    init {
        viewModelScope.launch {
            _persons.value = repository.getAllPersonsData()
        }
    }

    fun updatePerson(newPersonsData: List<Person>) {
        viewModelScope.launch {
            _persons.value = newPersonsData
        }
    }
}