package com.example.challenge5_afifuddin.login.database_login

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity
data class User(
    @PrimaryKey(autoGenerate = true) var id_user: Int?,
    @ColumnInfo(name = "username") var username:String?,
    @ColumnInfo(name = "email") var email: String?,
    @ColumnInfo(name = "password") var password: String?,
    @ColumnInfo(name = "nama_lengkap") var namaLengkap:String?,
    @ColumnInfo(name ="image") var image:String
) :Parcelable
