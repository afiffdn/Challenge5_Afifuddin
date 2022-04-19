package com.example.challenge5_afifuddin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.challenge5_afifuddin.adapter.NowMovieShowingAdapter
import com.example.challenge5_afifuddin.api.ApiClient
import com.example.challenge5_afifuddin.databinding.ActivityMainBinding
import com.example.challenge5_afifuddin.model_movies_now_showing.GetAllMovieNowShowing
import com.example.challenge5_afifuddin.model_movies_now_showing.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        fetchData()
    }

    private fun fetchData() {
        ApiClient.instance.getAllNowPlaying()
            .enqueue(object : Callback<GetAllMovieNowShowing>{
                override fun onResponse(
                    call: Call<GetAllMovieNowShowing>,
                    response: Response<GetAllMovieNowShowing>
                ) {
                    Log.d("cek",response.toString())
                    val body =response.body()
                    val code = response.code()
                    if (code == 200){
                        showList(body!!.results)
                    }
                }
                override fun onFailure(call: Call<GetAllMovieNowShowing>, t: Throwable) {
                  }
            })
    }

    private fun showList(data: List<Result>) {
        val adapter = NowMovieShowingAdapter(object :NowMovieShowingAdapter.onClickListener{
            override fun onClickItem(data: Result) {

            }
        })
        adapter.submitData(data)
        binding.rvMovie.adapter = adapter
    }
}