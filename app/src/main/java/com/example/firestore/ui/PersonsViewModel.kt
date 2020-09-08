package com.example.firestore.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.firestore.data.model.Filter
import com.example.firestore.data.model.Person
import com.example.firestore.data.repository.AppRepository
import javax.inject.Inject

class PersonsViewModel @Inject constructor(repository: AppRepository) : ViewModel() {

    var filterObservable = false
    var deleteObservable = false

    val resultOfSnapshot = repository.resultOfSnapshot

    private val _filter = MutableLiveData<Filter>()

    val filteredPersons = _filter.switchMap { repository.search(it) }

    private val _deletePerson = MutableLiveData<Person>()

    val resultOfDeletePerson = _deletePerson.switchMap { repository.deletePerson(it) }

    fun setFilter(filter: Filter) {
        filterObservable = true
        _filter.value = filter
    }

    fun deletePerson(person: Person) {
        deleteObservable = true
        _deletePerson.value = person
    }

}