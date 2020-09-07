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

    val resultOfSnapshot = repository.resultOfSnapshot

    val filter = MutableLiveData<Filter>()

    val filteredPersons = filter.switchMap { repository.search(it) }

}