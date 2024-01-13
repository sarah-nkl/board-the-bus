package com.appcessible.boardthebus.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.appcessible.boardthebus.database.dao.BusDao
import com.appcessible.boardthebus.database.dao.BusStopDao
import com.appcessible.boardthebus.database.dao.FavoriteBusStopDao
import com.appcessible.boardthebus.database.entity.Bus
import com.appcessible.boardthebus.database.entity.BusStop
import com.appcessible.boardthebus.database.entity.FavoriteBusStop
import com.appcessible.boardthebus.workers.UpdateWorker

@Database(entities = [Bus::class, BusStop::class, FavoriteBusStop::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun busDao(): BusDao
    abstract fun busStopDao(): BusStopDao
    abstract fun favoriteBusStopDao(): FavoriteBusStopDao

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "bus_db")
                .addCallback(
                    object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<UpdateWorker>()
                                .build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }
                )
                .build()
        }
    }
}