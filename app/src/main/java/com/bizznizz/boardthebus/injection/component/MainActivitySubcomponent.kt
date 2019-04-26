package com.bizznizz.boardthebus.injection.component

import com.bizznizz.boardthebus.MainActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface MainActivitySubcomponent : AndroidInjector<MainActivity> {
    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<MainActivity>
}