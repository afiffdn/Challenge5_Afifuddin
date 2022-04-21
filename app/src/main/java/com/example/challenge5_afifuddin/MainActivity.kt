package com.example.challenge5_afifuddin

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.challenge5_afifuddin.adapter.NowMovieShowingAdapter
import com.example.challenge5_afifuddin.api.ApiClient
import com.example.challenge5_afifuddin.databinding.ActivityMainBinding
import com.example.challenge5_afifuddin.login.database_login.Database
import com.example.challenge5_afifuddin.model_movies_now_showing.GetAllMovieNowShowing
import com.example.challenge5_afifuddin.model_movies_now_showing.Result
import com.example.challenge5_afifuddin.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var dB : Database? = null
    private lateinit var mainViewModel : MainViewModel
    @SuppressLint("SetTextI18n", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mainViewModel= ViewModelProvider(this).get(MainViewModel::class.java)
        dB = Database.getInstance(this)
        val sharedPreferences = this.getSharedPreferences(RegisterActivity.FILE, Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username","default_user")
        Log.d("username",username.toString())
        mainViewModel.dataUser.observe(this){
            binding.tvWelcome.text = getString(R.id.tv_welcome, it.username)
        }
        if(username != null){
                getUsername(username)
        }

        fetchData()
        binding.ivUser.setOnClickListener {
         startActivity(Intent(this,UserActivity::class.java))
        }

    }

    private fun getUsername(username: String) {
        lifecycleScope.launch(Dispatchers.IO){
        val getuser = dB?.user()?.getUser(username)
            runBlocking(Dispatchers.Main){
                if (getuser != null){
                    mainViewModel.getUser(getuser)
                }
            }
        }
    }

    private fun fetchData() {
        ApiClient.instance.getAllNowPlaying()
            .enqueue(object : Callback<GetAllMovieNowShowing> {
                override fun onResponse(
                    call: Call<GetAllMovieNowShowing>,
                    response: Response<GetAllMovieNowShowing>
                ) {
                    val body = response.body()
                    val code = response.code()
                    if (code == 200) {
                        showList(body!!.results)
                    }
                }

                override fun onFailure(call: Call<GetAllMovieNowShowing>, t: Throwable) {
                }
            })
    }

    private fun showList(data: List<Result>) {
        val adapter = NowMovieShowingAdapter(object : NowMovieShowingAdapter.onClickListener {
            override fun onClickItem(data: Result) {
                val id = data.id
               val toDetail = Intent(this@MainActivity, DetailActivity::class.java).apply {
                    putExtra("id", id)
                }
                startActivity(toDetail)
            }
        })
        adapter.submitData(data)
        binding.rvMovie.adapter = adapter
    }
}