package com.appcessible.boardthebus.injection.component

import com.appcessible.boardthebus.MainActivity
import com.appcessible.boardthebus.injection.module.BusArrivalFragmentBindingModule
import com.appcessible.boardthebus.injection.module.FavoritesFragmentBindingModule
import com.appcessible.boardthebus.injection.module.MainActivityModule
import com.appcessible.boardthebus.injection.module.MainActivityScope
import com.appcessible.boardthebus.injection.module.SearchFragmentBindingModule
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = [
    MainActivityModule::class,
    FavoritesFragmentBindingModule::class,
    SearchFragmentBindingModule::class,
    BusArrivalFragmentBindingModule::class
])
@MainActivityScope
interface MainActivitySubcomponent : AndroidInjector<MainActivity> {
    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<MainActivity>
}