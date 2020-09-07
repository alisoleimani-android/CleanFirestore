package com.example.firestore.data.repository

import com.example.firestore.data.model.Person
import com.example.firestore.data.source.AppDateSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    private val source: AppDateSource
) {

    fun retrieve() = source.retrieve()

    fun addPerson(person: Person) = source.addPerson(person)

}