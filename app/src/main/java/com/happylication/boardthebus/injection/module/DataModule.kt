package com.happylication.boardthebus.injection.module

import android.content.Context
import androidx.room.Room
import com.happylication.boardthebus.BusArrivalService
import com.happylication.boardthebus.network.HeaderInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.happylication.boardthebus.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
@Suppress("unused")
object DataModule {

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRoomDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "board-the-bus").build()
    }
}