package com.example.challenge5_afifuddin.room


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.challenge5_afifuddin.room.dao.UserDao
import com.example.challenge5_afifuddin.model.Favorite
import com.example.challenge5_afifuddin.room.dao.FavoriteDao
import com.example.challenge5_afifuddin.model.User

@Database(entities = [User::class, Favorite::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun user(): UserDao
    abstract fun favorite(): FavoriteDao
}