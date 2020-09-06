package com.example.firestore.di.builder

import com.example.firestore.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [FragmentBuilder::class])
    abstract fun contributeMainActivity(): MainActivity
}
