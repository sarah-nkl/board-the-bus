package com.happylication.boardthebus.injection.component

import com.happylication.boardthebus.MainActivity
import com.happylication.boardthebus.fragment.FavoritesFragment
import com.happylication.boardthebus.injection.module.FavoritesFragmentBindingModule
import com.happylication.boardthebus.injection.module.MainActivityModule
import com.happylication.boardthebus.injection.module.MainActivityScope
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = [MainActivityModule::class, FavoritesFragmentBindingModule::class])
@MainActivityScope
interface MainActivitySubcomponent : AndroidInjector<MainActivity> {
    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<MainActivity>
}