package com.example.challenge5_afifuddin.di

import com.example.challenge5_afifuddin.viewmodel.LoginViewModel
import com.example.challenge5_afifuddin.viewmodel.MainViewModel
import com.example.challenge5_afifuddin.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val ViewModelModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::MainViewModel)
}