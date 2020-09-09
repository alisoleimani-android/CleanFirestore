package com.example.firestore.data.repository

import com.example.firestore.data.model.Person
import com.example.firestore.data.source.AppDateSource
import com.example.firestore.ui.PersonsViewModel
import com.example.firestore.ui.RegisterViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(private val source: AppDateSource) {

    val resultOfSnapshot = source.resultOfSnapshot

    suspend fun addPerson(person: Person) = source.addPerson(person)

    suspend fun updatePerson(model: RegisterViewModel.UpdatePerson) = source.updatePerson(model)

    suspend fun deletePerson(person: Person) = source.deletePerson(person)

    suspend fun increaseOrDecreaseValueOfAge(model: PersonsViewModel.IncreaseOrDecrease) =
        source.increaseOrDecreaseAge(model)

    suspend fun search(filter: PersonsViewModel.Filter) = source.search(filter)

}