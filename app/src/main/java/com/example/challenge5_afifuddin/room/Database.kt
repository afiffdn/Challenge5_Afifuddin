package com.example.challenge5_afifuddin.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.challenge5_afifuddin.room.dao.UserDao
import com.example.challenge5_afifuddin.model.Favorite
import com.example.challenge5_afifuddin.room.dao.FavoriteDao
import com.example.challenge5_afifuddin.model.User

@Database(entities = [User::class, Favorite::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun user(): UserDao
    abstract fun favorite(): FavoriteDao

    companion object {
       private var INSTANCE: com.example.challenge5_afifuddin.room.Database? = null

        fun getInstance(context: Context): com.example.challenge5_afifuddin.room.Database? {
            if (INSTANCE == null) {
                synchronized(com.example.challenge5_afifuddin.room.Database::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        com.example.challenge5_afifuddin.room.Database
                        ::class.java, "AppsDatabase.db"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}