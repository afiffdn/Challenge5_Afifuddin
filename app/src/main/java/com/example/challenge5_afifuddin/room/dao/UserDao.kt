package com.example.challenge5_afifuddin.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.example.challenge5_afifuddin.model.User


@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getAllUser(): List<User>

    @Insert(onConflict = REPLACE)
    suspend fun insertUser (user: User)

    @Query ("SELECT*FROM User WHERE email = :email AND password = :password")
    fun checkUser(email:String,password :String): User

    @Update
    fun updateUser(user: User):Int

    @Query("SELECT * FROM user WHERE username = :username")
    fun getUser(username: String): User
}