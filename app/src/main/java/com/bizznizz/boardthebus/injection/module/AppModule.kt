package com.bizznizz.boardthebus.injection.module

import android.app.Application
import com.bizznizz.boardthebus.MyApplication
import dagger.Binds
import dagger.Module

@Module
// Safe here as we are dealing with a Dagger 2 module
@Suppress("unused")
interface AppModule {

    @Binds
    fun provideContext(impl: MyApplication): Application
}