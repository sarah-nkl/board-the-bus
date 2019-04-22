package com.bizznizz.boardthebus.injection.component

import com.bizznizz.boardthebus.MainActivity
import com.bizznizz.boardthebus.MyApplication
import com.bizznizz.boardthebus.injection.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface AppComponent {

    fun inject(app: MyApplication)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        fun networkModule(networkModule: NetworkModule): Builder
    }
}