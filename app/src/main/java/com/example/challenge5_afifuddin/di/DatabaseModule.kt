package com.example.challenge5_afifuddin.di

import androidx.room.Room
import com.example.challenge5_afifuddin.room.Database
import com.example.challenge5_afifuddin.room.DbHelper
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext().applicationContext,
            Database::class.java,
            "Database"
        ).build()
    }
    single {
        get<Database>().user()
    }
    singleOf(::DbHelper)
}