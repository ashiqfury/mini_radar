package com.example.miniradar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.miniradar.data.repository.PersonRepository

class ViewModelFactory constructor(private val repository: PersonRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AgentsCardViewModel::class.java)) {
            AgentsCardViewModel(repository = this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel not found!")
        }
    }
}