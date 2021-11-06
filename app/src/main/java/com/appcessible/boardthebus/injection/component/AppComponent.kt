package com.appcessible.boardthebus.injection.component

import com.appcessible.boardthebus.MyApplication
import com.appcessible.boardthebus.injection.module.AppModule
import com.appcessible.boardthebus.injection.module.DataModule
import com.appcessible.boardthebus.injection.module.MainActivityBindingModule
import com.appcessible.boardthebus.injection.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    NetworkModule::class,
    DataModule::class,
    AndroidInjectionModule::class,
    MainActivityBindingModule::class
])
interface AppComponent : AndroidInjector<MyApplication> {

    @Component.Factory
    interface Factory : AndroidInjector.Factory<MyApplication> {
        override fun create(@BindsInstance instance: MyApplication): AppComponent
    }
}