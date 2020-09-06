package com.example.firestore.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.firestore.data.repository.PersonRepository
import javax.inject.Inject

class PersonsViewModel @Inject constructor(
    repository: PersonRepository
) : ViewModel() {

    private val _retrieve = MutableLiveData<Boolean>()

    init {
        retrieveData()
    }

    fun retrieveData() {
        _retrieve.postValue(true)
    }

    val retrieveResult = _retrieve.switchMap {
        repository.retrieveData()
    }

}