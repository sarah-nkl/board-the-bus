package com.bizznizz.boardthebus.injection.module

import androidx.lifecycle.Lifecycle
import com.bizznizz.boardthebus.MainActivity
import com.bizznizz.boardthebus.injection.component.MainActivitySubcomponent
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import javax.inject.Scope

@Module(subcomponents = [MainActivitySubcomponent::class])
abstract class MainActivityBindingModule {

    @Binds
    @IntoMap
    @ClassKey(MainActivity::class)
    abstract fun bindMainActivityInjectorFactory(factory: MainActivitySubcomponent.Factory): AndroidInjector.Factory<*>
}

@Module
class MainActivityModule {
    @Provides
    @MainActivityScope
    fun provideLifecycle(activity: MainActivity): Lifecycle = activity.lifecycle
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class MainActivityScope
