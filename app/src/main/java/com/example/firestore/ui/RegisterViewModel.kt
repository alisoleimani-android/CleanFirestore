package com.example.firestore.ui

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firestore.data.Collections
import com.example.firestore.data.Person
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val context: Context
) : ViewModel() {

    private val personsReference = Firebase.firestore.collection(Collections.PERSONS)

    fun addPerson(person: Person) = viewModelScope.launch(Dispatchers.IO) {
        try {
            personsReference.add(person).await()

            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Successfully added to database", Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }

    }
}