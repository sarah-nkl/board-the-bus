package com.appcessible.boardthebus.injection.module

import android.content.Context
import androidx.room.Room
import com.appcessible.boardthebus.TimeFormatter
import com.appcessible.boardthebus.TimeFormatterHelper
import com.appcessible.boardthebus.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
@Suppress("unused")
object DataModule {

    @Provides
    @Reusable
    internal fun provideRoomDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "board-the-bus").build()
    }

    @Provides
    @Reusable
    internal fun provideTimeFormatter(): TimeFormatter {
        return TimeFormatterHelper()
    }
}