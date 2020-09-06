package com.example.firestore.di.builder

import com.example.firestore.ui.PersonsFragment
import com.example.firestore.ui.RegisterFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Fragment contributes for injection of app component, defined here
 */
@Suppress("unused")
@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract fun contributePersonsFragment(): PersonsFragment

    @ContributesAndroidInjector
    abstract fun contributeRegisterFragment(): RegisterFragment

}
