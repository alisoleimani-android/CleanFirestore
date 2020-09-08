package com.example.firestore.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.firestore.data.model.Person
import com.example.firestore.data.model.UpdatePersonModel
import com.example.firestore.data.repository.AppRepository
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _addPerson = MutableLiveData<Person>()

    val resultOfAddPerson = _addPerson.switchMap {
        repository.addPerson(it)
    }

    private val _updatePerson = MutableLiveData<UpdatePersonModel>()

    val resultOfUpdatePerson = _updatePerson.switchMap {
        repository.updatePerson(it)
    }

    fun updatePerson(person: Person, firstName: String, lastName: String, age: String) {
        val changes = mutableMapOf<String, Any>()

        if (firstName.isNotEmpty()) {
            changes["firstName"] = firstName
        }

        if (lastName.isNotEmpty()) {
            changes["lastName"] = lastName
        }

        if (age.isNotEmpty()) {
            changes["age"] = age.toInt()
        }

        _updatePerson.value = UpdatePersonModel(person, changes)
    }

    fun addNewPerson(firstName: String, lastName: String, age: String) {
        _addPerson.value = Person(firstName, lastName, age.toInt())
    }

}