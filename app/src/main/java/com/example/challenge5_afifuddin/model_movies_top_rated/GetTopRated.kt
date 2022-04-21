package com.example.challenge5_afifuddin.model_movies_top_rated


import com.google.gson.annotations.SerializedName

data class GetTopRated(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)