package com.example.challenge5_afifuddin.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challenge5_afifuddin.model_movies_now_showing.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel :ViewModel() {

    val movieDetail :MutableLiveData<Result> = MutableLiveData()

    fun getDetails(id :Int){
//        ApiClient.instance.getDetails(movieId = id)
//            .enqueue(object : Callback<Result>{
//                override fun onResponse(call: Call<Result>, response: Response<Result>) {
//            movieDetail.postValue(response.body())
//                }
//
//                override fun onFailure(call: Call<Result>, t: Throwable) {
//                    Log.d("getDetail","${t.message}")
//                }
//            })
    }


}