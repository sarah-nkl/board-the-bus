package com.happylication.boardthebus.injection.module

import android.content.Context
import com.happylication.boardthebus.MyApplication
import dagger.Binds
import dagger.Module

@Module
@Suppress("unused")
interface AppModule {

    @Binds
    fun provideContext(impl: MyApplication): Context
}