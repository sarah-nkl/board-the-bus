package com.bizznizz.boardthebus.injection.component

import com.bizznizz.boardthebus.MyApplication
import com.bizznizz.boardthebus.injection.module.AppModule
import com.bizznizz.boardthebus.injection.module.MainActivityBindingModule
import com.bizznizz.boardthebus.injection.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    NetworkModule::class,
    AndroidInjectionModule::class,
    MainActivityBindingModule::class
])
interface AppComponent : AndroidInjector<MyApplication> {

    @Component.Factory
    interface Factory : AndroidInjector.Factory<MyApplication> {
        override fun create(@BindsInstance instance: MyApplication): AppComponent
    }
}