package com.example.firestore.ui

import androidx.lifecycle.ViewModel
import com.example.firestore.data.repository.AppRepository
import javax.inject.Inject

class PersonsViewModel @Inject constructor(
    repository: AppRepository
) : ViewModel() {

    val resultOfSnapshot = repository.resultOfSnapshot

}