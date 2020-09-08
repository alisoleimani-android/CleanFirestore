package com.example.firestore.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.firestore.data.model.Filter
import com.example.firestore.data.repository.AppRepository
import javax.inject.Inject

class PersonsViewModel @Inject constructor(
    repository: AppRepository
) : ViewModel() {

    var filterObservable = false

    val resultOfSnapshot = repository.resultOfSnapshot

    private val _filter = MutableLiveData<Filter>()

    val filteredPersons = _filter.switchMap { repository.search(it) }

    fun setFilter(filter: Filter) {
        filterObservable = true
        _filter.value = filter
    }

}