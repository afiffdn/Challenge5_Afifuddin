package com.example.challenge5_afifuddin.api

import com.example.challenge5_afifuddin.model_movies_now_showing.GetAllMovieNowShowing
import com.example.challenge5_afifuddin.model_movies_now_showing.Result
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    fun getAllNowPlaying(@Query ("api_key") apiKey:String = ApiClient.API_KEY  ): retrofit2.Call<GetAllMovieNowShowing>

    @GET("movie/{movie_id}")
    fun getDetails(@Path("movie_id") movieId:Int, @Query("api_key") apiKey: String = ApiClient.API_KEY  ) : retrofit2.Call<Result>
}