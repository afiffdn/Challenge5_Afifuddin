package com.example.challenge5_afifuddin.room

import com.example.challenge5_afifuddin.model.User
import com.example.challenge5_afifuddin.room.dao.UserDao

class DbHelper(private val userDao: UserDao) {
    suspend fun insert(user :User) = userDao.insertUser(user)
    suspend fun check(email:String,password:String): User = userDao.checkUser(email, password)
    suspend fun update(user: User) = userDao.insertUser(user)
}