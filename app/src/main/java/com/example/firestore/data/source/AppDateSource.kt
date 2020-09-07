package com.example.firestore.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firestore.data.model.Person
import com.example.firestore.data.model.response.Result
import com.example.firestore.data.source.base.BaseDataSource
import com.example.firestore.di.PersonsReference
import com.google.firebase.firestore.CollectionReference
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

    fun addPerson(person: Person) = getResult {
        personsRef.add(person).await()
    }

}