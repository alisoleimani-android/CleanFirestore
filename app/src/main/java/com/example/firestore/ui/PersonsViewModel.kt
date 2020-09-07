package com.example.firestore.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.firestore.data.repository.AppRepository
import javax.inject.Inject

class PersonsViewModel @Inject constructor(
    repository: AppRepository
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