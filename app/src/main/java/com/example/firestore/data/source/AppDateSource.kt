package com.example.firestore.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firestore.data.model.Person
import com.example.firestore.data.model.response.Result
import com.example.firestore.data.source.base.BaseDataSource
import com.example.firestore.di.PersonsReference
import com.example.firestore.ui.PersonsViewModel
import com.example.firestore.ui.RegisterViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AppDateSource @Inject constructor(
    @PersonsReference
    private val personsRef: CollectionReference
) : BaseDataSource() {

    private val _snapshot = MutableLiveData<Result<List<Person>>>()
    val resultOfSnapshot: LiveData<Result<List<Person>>> = _snapshot

    init {
        personsRef.addSnapshotListener { value, error ->
            error?.let {
                _snapshot.postValue(Result.Error(it.message))
            }

            value?.let {
                _snapshot.postValue(Result.Success(it.toObjects(Person::class.java)))
            }
        }
    }

    suspend fun search(filter: PersonsViewModel.Filter) = getResult {
        personsRef
            .whereEqualTo("firstName", filter.name)
            .whereGreaterThan("age", filter.fromAge)
            .whereLessThan("age", filter.toAge)
            .orderBy("age")
            .get()
            .await()
            .toObjects(Person::class.java)
    }

    suspend fun addPerson(person: Person) = getResult {
        personsRef.add(person).await()
    }

    suspend fun updatePerson(model: RegisterViewModel.UpdatePerson) = getResult {
        getDocumentOfPerson(model.person)?.set(model.changesMap, SetOptions.merge())?.await()
    }

    suspend fun deletePerson(person: Person) = getResult {
        getDocumentOfPerson(person)?.delete()?.await()
    }

    suspend fun increaseOrDecreaseAge(model: PersonsViewModel.IncreaseOrDecrease) = getResult {
        val id = getDocumentOfPerson(model.person)?.id ?: return@getResult

        Firebase.firestore.runTransaction { transaction ->
            val personRef = personsRef.document(id)
            val person = transaction.get(personRef)
            val newAge = person["age"] as Long + model.value
            transaction.update(personRef, "age", newAge)
        }.await()
    }

    private suspend fun getDocumentOfPerson(person: Person): DocumentReference? {
        val personQuery = personsRef
            .whereEqualTo("firstName", person.firstName)
            .whereEqualTo("lastName", person.lastName)
            .whereEqualTo("age", person.age)
            .get()
            .await()
        return personsRef.document(personQuery.documents.firstOrNull()?.id ?: return null)
    }
}