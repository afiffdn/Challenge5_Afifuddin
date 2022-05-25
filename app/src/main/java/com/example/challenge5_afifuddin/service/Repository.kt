package com.example.challenge5_afifuddin.service

import com.example.challenge5_afifuddin.model.User
import com.example.challenge5_afifuddin.room.DbHelper

class Repository(
    private val dbHelper: DbHelper,
    private val apiService: ApiService
) {
    //room
    suspend fun insert(user: User) = dbHelper.insert(user)
    suspend fun check(email:String, password:String) : User =dbHelper.check(email,password)
    //api
    suspend fun getAllMovieNowShowing() = apiService.getAllNowShowing()
}