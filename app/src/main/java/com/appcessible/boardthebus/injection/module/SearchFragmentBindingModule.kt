package com.appcessible.boardthebus.injection.module

import com.appcessible.boardthebus.fragment.SearchFragment
import com.appcessible.boardthebus.injection.component.SearchFragmentSubcomponent
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import javax.inject.Scope

@Module(subcomponents = [SearchFragmentSubcomponent::class])
abstract class SearchFragmentBindingModule {

    @Binds
    @IntoMap
    @ClassKey(SearchFragment::class)
    abstract fun bindSearchFragmentInjectorFactory(factory: SearchFragmentSubcomponent.Factory): AndroidInjector.Factory<*>
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class SearchFragmentScope
