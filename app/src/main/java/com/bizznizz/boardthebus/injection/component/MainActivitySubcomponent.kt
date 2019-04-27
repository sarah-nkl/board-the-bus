package com.bizznizz.boardthebus.injection.component

import com.bizznizz.boardthebus.MainActivity
import com.bizznizz.boardthebus.injection.module.MainActivityModule
import com.bizznizz.boardthebus.injection.module.MainActivityScope
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = [MainActivityModule::class])
@MainActivityScope
interface MainActivitySubcomponent : AndroidInjector<MainActivity> {
    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<MainActivity>
}