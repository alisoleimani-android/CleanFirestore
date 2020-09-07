package com.example.firestore.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.firestore.data.model.Person
import com.example.firestore.data.repository.AppRepository
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    val addPerson = MutableLiveData<Person>()

    val resultOfAddPerson = addPerson.switchMap {
        repository.addPerson(it)
    }

}