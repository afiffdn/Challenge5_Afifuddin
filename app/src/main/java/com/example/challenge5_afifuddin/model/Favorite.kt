package com.example.challenge5_afifuddin.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    var id_favorite: Int?,
    var id_user: Int?,
    var movie_name: String?,
    var tahun :String?,
    var deskr:String,
    var image:String
) : Parcelable

