package com.example.challenge5_afifuddin.service

import com.example.challenge5_afifuddin.model.User
import com.example.challenge5_afifuddin.room.DbHelper

class Repository(
    private val dbHelper: DbHelper
) {
    suspend fun insertUser(user: User) = dbHelper.insert(user)
}