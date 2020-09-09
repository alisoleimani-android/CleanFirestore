package com.example.firestore.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.example.firestore.data.model.Person
import com.example.firestore.data.repository.AppRepository
import com.example.firestore.ui.base.BaseViewModel
import javax.inject.Inject

class PersonsViewModel @Inject constructor(repository: AppRepository) : BaseViewModel() {

    val snapshot = repository.resultOfSnapshot

    private val _filter = MutableLiveData<Filter>()
    val filteredPersons = _filter.switchMap {
        watchResult { repository.search(it) }
    }

    private val _deletePerson = MutableLiveData<Person>()
    val onPersonDeleted = _deletePerson.switchMap {
        watchResult { repository.deletePerson(it) }
    }

    private val _changeValueOfAge = MutableLiveData<IncreaseOrDecrease>()
    val onAgeChanged = _changeValueOfAge.switchMap {
        watchResult { repository.increaseOrDecreaseValueOfAge(it) }
    }


    fun increaseOrDecreaseValueOfAge(person: Person, value: Int) {
        _changeValueOfAge.value = IncreaseOrDecrease(person, value)
    }

    fun setFilter(filter: Filter) {
        _filter.value = filter
    }

    fun deletePerson(person: Person) {
        _deletePerson.value = person
    }

    data class IncreaseOrDecrease(
        val person: Person,
        val value: Int
    )

    data class Filter(
        val name: String = "",
        val fromAge: Int = -1,
        val toAge: Int = 200
    )

}