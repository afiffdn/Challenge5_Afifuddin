package com.example.challenge5_afifuddin.room.dao

import androidx.room.*
import com.example.challenge5_afifuddin.model.Favorite


@Dao
interface FavoriteDao {
    @Query("SELECT * FROM Favorite WHERE id_user= :id_user AND movie_name= :movie_name")
    fun cekFavorite(id_user:Int , movie_name:String):Boolean
    @Query("SELECT * FROM Favorite WHERE id_user = :id_user")
    fun getFavorite(id_user: Int): Favorite
    @Query("SELECT * FROM Favorite WHERE id_user= :id_user AND movie_name = :movie_name")
    fun getFavoriteByIdAndCountry(id_user: Int, movie_name: String): Favorite
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(favorite: Favorite):Long
    @Delete
    fun deleteFavorite(favorite: Favorite): Int
}