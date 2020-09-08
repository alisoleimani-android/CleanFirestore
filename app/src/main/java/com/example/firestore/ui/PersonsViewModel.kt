package com.example.firestore.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.firestore.data.model.Person
import com.example.firestore.data.repository.AppRepository
import javax.inject.Inject

class PersonsViewModel @Inject constructor(repository: AppRepository) : ViewModel() {

    var filterObservable = false
    var deleteObservable = false
    var increaseOrDecreaseObservable = false

    val resultOfSnapshot = repository.resultOfSnapshot

    private val _filter = MutableLiveData<Filter>()
    val filteredPersons = _filter.switchMap { repository.search(it) }

    private val _deletePerson = MutableLiveData<Person>()
    val resultOfDeletePerson = _deletePerson.switchMap { repository.deletePerson(it) }

    private val _changeValueOfAge = MutableLiveData<IncreaseOrDecrease>()
    val resultOfIncreaseOrDecreaseAge =
        _changeValueOfAge.switchMap { repository.increaseOrDecreaseValueOfAge(it) }


    fun increaseOrDecreaseValueOfAge(person: Person, value: Int) {
        increaseOrDecreaseObservable = true
        _changeValueOfAge.value = IncreaseOrDecrease(person, value)
    }

    fun setFilter(filter: Filter) {
        filterObservable = true
        _filter.value = filter
    }

    fun deletePerson(person: Person) {
        deleteObservable = true
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