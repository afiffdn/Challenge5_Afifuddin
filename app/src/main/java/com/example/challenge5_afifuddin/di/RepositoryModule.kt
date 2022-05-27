package com.example.challenge5_afifuddin.di



import com.example.challenge5_afifuddin.service.Repository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val RepositoryModule = module {
    singleOf(::Repository)
}