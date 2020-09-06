package com.example.firestore.di.component

import android.app.Application
import com.example.firestore.App
import com.example.firestore.di.builder.ActivityBuilder
import com.example.firestore.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Provides required modules for app. This component
 * belongs to the whole application.
 */
@Singleton // Because only one instance is needed
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivityBuilder::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(application: App)
}
