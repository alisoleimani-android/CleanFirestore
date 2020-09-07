package com.example.firestore.data.source

import com.example.firestore.data.model.Person
import com.example.firestore.data.source.base.BaseDataSource
import com.example.firestore.di.PersonsReference
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AppDateSource @Inject constructor(
    @PersonsReference
    private val personsRef: CollectionReference
) : BaseDataSource() {

    fun retrieve() = getResult {
        personsRef.get().await().toObjects(Person::class.java)
    }

    fun addPerson(person: Person) = getResult {
        personsRef.add(person).await()
    }

}