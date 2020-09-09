package com.example.firestore.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.example.firestore.data.model.Person
import com.example.firestore.data.repository.AppRepository
import com.example.firestore.ui.base.BaseViewModel
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val repository: AppRepository
) : BaseViewModel() {

    private val _addPerson = MutableLiveData<Person>()
    val onPersonAddedSuccessfully = _addPerson.switchMap {
        watchResult { repository.addPerson(it) }
    }

    private val _updatePerson = MutableLiveData<UpdatePerson>()
    val onPersonUpdatedSuccessfully = _updatePerson.switchMap {
        watchResult { repository.updatePerson(it) }
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

        _updatePerson.value = UpdatePerson(person, changes)
    }

    fun addNewPerson(firstName: String, lastName: String, age: String) {
        _addPerson.value = Person(firstName, lastName, age.toInt())
    }

    data class UpdatePerson(
        val person: Person,
        val changesMap: Map<String, Any>
    )

}