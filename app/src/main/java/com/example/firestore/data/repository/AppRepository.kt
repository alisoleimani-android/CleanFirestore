package com.example.firestore.data.repository

import com.example.firestore.data.model.Filter
import com.example.firestore.data.model.Person
import com.example.firestore.data.source.AppDateSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    private val source: AppDateSource
) {

    val resultOfSnapshot = source.resultOfSnapshot

    fun addPerson(person: Person) = source.addPerson(person)

    fun search(filter: Filter) = source.search(filter)

}