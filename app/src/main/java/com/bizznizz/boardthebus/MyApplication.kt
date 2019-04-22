package com.bizznizz.boardthebus

import android.app.Activity
import android.app.Application
import com.bizznizz.boardthebus.injection.component.DaggerAppComponent
import com.bizznizz.boardthebus.injection.module.NetworkModule
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject



class MyApplication : Application(), HasActivityInjector {

    @Inject lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        val appComponent = DaggerAppComponent.builder()
            .networkModule(NetworkModule)
            .build()
        appComponent.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingAndroidInjector
    }
}