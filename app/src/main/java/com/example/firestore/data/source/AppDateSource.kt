package com.example.firestore.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firestore.data.model.Filter
import com.example.firestore.data.model.Person
import com.example.firestore.data.model.UpdatePersonModel
import com.example.firestore.data.model.response.Result
import com.example.firestore.data.source.base.BaseDataSource
import com.example.firestore.di.PersonsReference
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.SetOptions
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

    fun search(filter: Filter): LiveData<Result<List<Person>>> = getResult {
        personsRef
            .whereEqualTo("firstName", filter.name)
            .whereGreaterThan("age", filter.fromAge)
            .whereLessThan("age", filter.toAge)
            .orderBy("age")
            .get()
            .await()
            .toObjects(Person::class.java)
    }

    fun addPerson(person: Person): LiveData<Result<DocumentReference>> = getResult {
        personsRef
            .add(person)
            .await()
    }

    fun updatePerson(model: UpdatePersonModel): LiveData<Result<Unit>> = getResult {
        val personQuery = personsRef
            .whereEqualTo("firstName", model.person.firstName)
            .whereEqualTo("lastName", model.person.lastName)
            .whereEqualTo("age", model.person.age)
            .get()
            .await()
        personQuery.documents.firstOrNull()?.let {
            personsRef.document(it.id).set(model.changesMap, SetOptions.merge()).await()
        }
    }

    fun deletePerson(person: Person): LiveData<Result<Unit>> = getResult {
        val personQuery = personsRef
            .whereEqualTo("firstName", person.firstName)
            .whereEqualTo("lastName", person.lastName)
            .whereEqualTo("age", person.age)
            .get()
            .await()
        personQuery.documents.firstOrNull()?.let {
            personsRef.document(it.id).delete().await()
        }
    }

}