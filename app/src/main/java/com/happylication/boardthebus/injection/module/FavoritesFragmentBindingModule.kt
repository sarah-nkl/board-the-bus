package com.happylication.boardthebus.injection.module

import com.happylication.boardthebus.fragment.FavoritesFragment
import com.happylication.boardthebus.injection.component.FavoritesFragmentSubcomponent
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
