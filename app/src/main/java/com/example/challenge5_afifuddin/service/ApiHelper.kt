package com.example.challenge5_afifuddin.service

class ApiHelper(private val apiService: ApiService) {
    suspend fun getMovieNowShowing() = apiService.getAllNowShowing()
    suspend fun getTopRated() = apiService.getTopRated()
}