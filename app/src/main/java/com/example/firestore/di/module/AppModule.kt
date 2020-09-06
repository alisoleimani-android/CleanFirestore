package com.example.firestore.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Suppress("unused")
@Module(includes = [ViewModelModule::class, DataSourceModule::class])
class AppModule {

    /**
     * Provides application context.
     * @param app, instance of [Application]
     */
    @Singleton
    @Provides
    fun provideContext(app: Application): Context = app.applicationContext
}
