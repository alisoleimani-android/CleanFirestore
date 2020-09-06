package com.example.firestore.di.module

import android.app.Application
import android.content.Context
import com.example.firestore.di.IOScope
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Suppress("unused")
@Module(includes = [ViewModelModule::class])
class AppModule {

    /**
     * Provides application context.
     * @param app, instance of [Application]
     */
    @Singleton
    @Provides
    fun provideContext(app: Application): Context = app.applicationContext

    /**
     * Provides an IO scope of [Dispatchers]
     */
    @IOScope
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)
}
