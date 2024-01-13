package com.appcessible.boardthebus

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.work.Configuration
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.appcessible.boardthebus.injection.component.AppComponent
import com.appcessible.boardthebus.injection.component.DaggerAppComponent
import com.appcessible.boardthebus.workers.BusWorkerFactory
import com.appcessible.boardthebus.workers.UpdateWorker
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

private val LAST_DB_UPDATE_DATE = longPreferencesKey("last_db_update_date")

private const val WEEK_IN_MS = 7 * 24 * 60 * 60 * 1000L

class MyApplication : Application(), HasAndroidInjector, Configuration.Provider {

    @Inject lateinit var androidInjector: DispatchingAndroidInjector<Any>
    @Inject lateinit var busWorkerFactory: BusWorkerFactory

    lateinit var appComponent: AppComponent

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        appComponent = DaggerAppComponent.factory().create(this)
        appComponent.inject(this)

        applicationScope.launch {
            try {
                setupUpdateWorker()
            } catch (e: Exception) {
                Log.e("MyApplication", "Failed to set up UpdateWorker", e)
            }
        }
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(busWorkerFactory)
            .build()

    private suspend fun setupUpdateWorker() {
        val currentTimeInMs = System.currentTimeMillis()
        dataStore.data
            .map { preferences ->
                preferences[LAST_DB_UPDATE_DATE] ?: currentTimeInMs
            }.collect {
                Log.d("MyApplication", "last db update = $it, current time in ms = $currentTimeInMs")

                var updatePref = false

                if (currentTimeInMs - it >= WEEK_IN_MS) {
                    val request = OneTimeWorkRequestBuilder<UpdateWorker>()
                        .build()
                    WorkManager.getInstance(this).enqueue(request)
                    updatePref = true
                }

                if (it == currentTimeInMs || updatePref) {
                    dataStore.edit { settings ->
                        settings[LAST_DB_UPDATE_DATE] = currentTimeInMs
                    }
                }
            }
    }

}