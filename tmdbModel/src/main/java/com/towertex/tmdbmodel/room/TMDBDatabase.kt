package com.towertex.tmdbmodel.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.towertex.tmdbmodel.model.Configuration
import com.towertex.tmdbmodel.model.RowItem

@Database(
    entities = [
        RowItem::class,
        Configuration::class
    ],
    version = 5,
    exportSchema = false
)
abstract class TMDBDatabase : RoomDatabase() {
    abstract val tmdbDao: TMDBDao

    companion object {
        private const val DATABASE_NAME = "tmdbDatabase"

        fun buildDatabase(context: Context): TMDBDatabase = Room
            .databaseBuilder(context, TMDBDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}