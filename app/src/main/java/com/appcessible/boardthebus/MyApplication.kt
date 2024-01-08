package com.appcessible.boardthebus

import android.app.Application
import androidx.work.Configuration
import com.appcessible.boardthebus.injection.component.AppComponent
import com.appcessible.boardthebus.injection.component.DaggerAppComponent
import com.appcessible.boardthebus.workers.BusWorkerFactory
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MyApplication : Application(), HasAndroidInjector, Configuration.Provider {

    @Inject lateinit var androidInjector: DispatchingAndroidInjector<Any>
    @Inject lateinit var busWorkerFactory: BusWorkerFactory

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

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(busWorkerFactory)
            .build()
}