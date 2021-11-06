package com.appcessible.boardthebus.injection.module

import com.appcessible.boardthebus.fragment.FavoritesFragment
import com.appcessible.boardthebus.injection.component.FavoritesFragmentSubcomponent
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import javax.inject.Scope

@Module(subcomponents = [FavoritesFragmentSubcomponent::class])
abstract class FavoritesFragmentBindingModule {

    @Binds
    @IntoMap
    @ClassKey(FavoritesFragment::class)
    abstract fun bindFavoritesFragmentInjectorFactory(factory: FavoritesFragmentSubcomponent.Factory): AndroidInjector.Factory<*>
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FavoritesFragmentScope
