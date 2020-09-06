package com.example.firestore.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.firestore.data.Collections
import com.example.firestore.data.Person
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PersonsViewModel @Inject constructor() : ViewModel() {

    private val personsCollection = Firebase.firestore.collection(Collections.PERSONS)

    val persons = liveData(Dispatchers.IO) {
        val persons: List<Person> = personsCollection.get().await().toObjects(Person::class.java)
        emit(persons)
    }

}