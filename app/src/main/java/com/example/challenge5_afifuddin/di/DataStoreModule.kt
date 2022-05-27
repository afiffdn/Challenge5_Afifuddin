package com.example.challenge5_afifuddin.di

import com.example.challenge5_afifuddin.datastore.DatastoreManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val datastoreModule = module {
    singleOf(::DatastoreManager)
}