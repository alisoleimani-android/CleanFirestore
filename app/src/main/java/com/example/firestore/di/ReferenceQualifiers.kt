package com.example.firestore.di

import javax.inject.Qualifier

/**
 * We should create a qualifier for each Collection because
 * we need to make difference between them for injection.
 */

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class PersonsReference