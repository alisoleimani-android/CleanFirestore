package com.example.firestore.data.repository

import com.example.firestore.data.source.PersonDateSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonRepository @Inject constructor(
    private val source: PersonDateSource
) {

    fun retrieveData() = source.retrieveResult()

}