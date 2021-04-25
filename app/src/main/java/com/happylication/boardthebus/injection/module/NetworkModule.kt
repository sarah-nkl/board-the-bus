package com.happylication.boardthebus.injection.module

import com.happylication.boardthebus.BusArrivalService
import com.happylication.boardthebus.network.HeaderInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
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
object NetworkModule {

    @Provides
    @Reusable
    internal fun provideBusArrivalService(retrofit: Retrofit): BusArrivalService {
        return retrofit.create(BusArrivalService::class.java)
    }

    @Provides
    @Reusable
    internal fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor("AccountKey" to "dMnJYa7fSwysMqUpPItfbA=="))
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }

    @Provides
    @Reusable
    internal fun provideRetrofitInterface(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://datamall2.mytransport.sg/ltaodataservice/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }
}