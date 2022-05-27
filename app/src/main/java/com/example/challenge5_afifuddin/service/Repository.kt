package com.example.challenge5_afifuddin.service

import com.example.challenge5_afifuddin.datastore.DatastoreManager
import com.example.challenge5_afifuddin.model.User
import com.example.challenge5_afifuddin.room.DbHelper
import kotlinx.coroutines.flow.Flow

class Repository(
    private val dbHelper: DbHelper,
    private val apiHelper: ApiHelper,
    private val datastore : DatastoreManager
) {
    //room
    suspend fun insert(user: User) = dbHelper.insert(user)
    suspend fun check(email:String, password:String) : User =dbHelper.check(email,password)
    suspend fun update(user: User) = dbHelper.update(user)
    //api
    suspend fun getAllMovieNowShowing() = apiHelper.getMovieNowShowing()
    suspend fun getTopRated() = apiHelper.getTopRated()
    //datastore
    suspend fun saveUserPref (saveUser: User) = datastore.saveUser(saveUser)
    suspend fun getUserPref () : Flow<User> = datastore.getUser()
    suspend fun deleteUserPref() = datastore.delete()
}