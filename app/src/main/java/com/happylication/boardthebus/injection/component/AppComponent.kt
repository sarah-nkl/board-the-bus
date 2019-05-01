package com.happylication.boardthebus.injection.component

import com.happylication.boardthebus.MyApplication
import com.happylication.boardthebus.injection.module.AppModule
import com.happylication.boardthebus.injection.module.DataModule
import com.happylication.boardthebus.injection.module.MainActivityBindingModule
import com.happylication.boardthebus.injection.module.NetworkModule
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