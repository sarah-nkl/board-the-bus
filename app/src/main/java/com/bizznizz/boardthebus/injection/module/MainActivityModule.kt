package com.bizznizz.boardthebus.injection.module

import com.bizznizz.boardthebus.MainActivity
import com.bizznizz.boardthebus.injection.component.MainActivitySubcomponent
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [MainActivitySubcomponent::class])
abstract class MainActivityModule {

    @Binds
    @IntoMap
    @ClassKey(MainActivity::class)
    abstract fun bindMainActivityInjectorFactory(factory: MainActivitySubcomponent.Factory): AndroidInjector.Factory<*>
}