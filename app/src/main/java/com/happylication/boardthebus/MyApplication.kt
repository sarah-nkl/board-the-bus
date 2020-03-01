package com.happylication.boardthebus

import android.app.Activity
import android.app.Application
import com.facebook.stetho.Stetho
import com.happylication.boardthebus.injection.component.AppComponent
import com.happylication.boardthebus.injection.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MyApplication : Application(), HasAndroidInjector {

    @Inject lateinit var androidInjector: DispatchingAndroidInjector<Any>

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        appComponent = DaggerAppComponent.factory().create(this)
        appComponent.inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
}