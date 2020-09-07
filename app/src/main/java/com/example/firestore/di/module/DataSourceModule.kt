package com.example.firestore.di.module

import com.example.firestore.data.source.AppDateSource
import com.example.firestore.di.PersonsReference
import com.google.firebase.firestore.CollectionReference
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [CollectionsModule::class])
class DataSourceModule {

    @Singleton
    @Provides
    fun providePersonDataSource(
        @PersonsReference personsReference: CollectionReference
    ) = AppDateSource(personsReference)

}