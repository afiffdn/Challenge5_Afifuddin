package com.example.challenge5_afifuddin.login.database_login

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun user(): UserDao

    companion object {
       private var INSTANCE: com.example.challenge5_afifuddin.login.database_login.Database? = null

        fun getInstance(context: Context): com.example.challenge5_afifuddin.login.database_login.Database? {
            if (INSTANCE == null) {
                synchronized(com.example.challenge5_afifuddin.login.database_login.Database::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        com.example.challenge5_afifuddin.login.database_login.Database
                        ::class.java, "user.db"
                    )
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}