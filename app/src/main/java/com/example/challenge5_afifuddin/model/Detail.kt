package com.example.challenge5_afifuddin.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Detail(
    val tittle :String,
    val overview :String,
    val tahun :String,
    val poster : String
) :Parcelable
