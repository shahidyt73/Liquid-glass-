package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [CustomGlassIcon::class, FavoriteIcon::class],
    version = 1,
    exportSchema = false
)
abstract class IconPackDatabase : RoomDatabase() {
    abstract fun iconPackDao(): IconPackDao

    companion object {
        @Volatile
        private var INSTANCE: IconPackDatabase? = null

        fun getDatabase(context: Context): IconPackDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    IconPackDatabase::class.java,
                    "liquid_glass_icon_pack.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
