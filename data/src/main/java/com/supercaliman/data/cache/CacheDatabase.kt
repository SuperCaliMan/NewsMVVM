package com.supercaliman.data.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(NewsResponseEntity::class), version = 1, exportSchema = false)
abstract class CacheDatabase : RoomDatabase() {

    abstract fun cacheDao(): CacheDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CacheDatabase? = null

        fun getDatabase(context: Context): CacheDatabase? {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        CacheDatabase::class.java,
                        "prova_db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }

}
