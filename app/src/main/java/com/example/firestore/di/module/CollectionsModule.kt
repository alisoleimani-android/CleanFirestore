package com.example.firestore.di.module

import com.example.firestore.data.source.Collections
import com.example.firestore.di.PersonsReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class CollectionsModule {

    @PersonsReference
    @Reusable
    @Provides
    fun providePersonsCollection() = Firebase.firestore.collection(Collections.PERSONS)

}