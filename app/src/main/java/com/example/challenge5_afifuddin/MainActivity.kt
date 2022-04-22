package com.example.challenge5_afifuddin

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.challenge5_afifuddin.adapter.NowMovieShowingAdapter
import com.example.challenge5_afifuddin.adapter.TopRatedAdapter
import com.example.challenge5_afifuddin.api.ApiClient
import com.example.challenge5_afifuddin.databinding.ActivityMainBinding
import com.example.challenge5_afifuddin.login.database_login.Database
import com.example.challenge5_afifuddin.model_movies_now_showing.GetAllMovieNowShowing
import com.example.challenge5_afifuddin.model_movies_now_showing.Result
import com.example.challenge5_afifuddin.model_movies_top_rated.GetTopRated
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var dB: Database? = null
    private lateinit var preferences: SharedPreferences


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

     //   mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
     //   dB = Database.getInstance(this)


        preferences = this.getSharedPreferences(RegisterActivity.FILE,Context.MODE_PRIVATE)
        binding.tvWelcome.text = "Hello, ${preferences.getString("username","default_user")}"



//        mainViewModel.dataUser.observe(this) {
//            binding.tvWelcome.text = getString(R.string.welcome_username, it.username)
//        }
//        if (username != null) {
//            getUsername(username)
//        }

        fetchDataAllNowPlaying()
        fetchDataTopRated()
        binding.ivUser.setOnClickListener {
            startActivity(Intent(this, UserActivity::class.java))
        }

    }

    private fun fetchDataTopRated() {
        ApiClient.instance.getTopRated()
            .enqueue(object : Callback<GetTopRated> {
                override fun onResponse(call: Call<GetTopRated>, response: Response<GetTopRated>) {
                    val body = response.body()
                    val code = response.code()
                    if (code == 200) {
                        showListTop(body!!.results)
                    }
                }

                override fun onFailure(call: Call<GetTopRated>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun showListTop(results: List<com.example.challenge5_afifuddin.model_movies_top_rated.Result>) {
        val adapter = TopRatedAdapter(object : TopRatedAdapter.OnClickListener {
            override fun onClickItem(data: com.example.challenge5_afifuddin.model_movies_top_rated.Result) {
                val id = data.id
                val toDetail = Intent(this@MainActivity, DetailActivity::class.java).apply {
                    putExtra("id", id)
                }
                startActivity(Intent(toDetail))
            }
        })
        adapter.submitData(results)
        binding.rvMovieTopRated.adapter = adapter
    }

//    private fun getUsername(username: String) {
//        lifecycleScope.launch(Dispatchers.IO) {
//            val getuser = dB?.user()?.getUser(username)
//            runBlocking(Dispatchers.Main) {
//                if (getuser != null) {
//                    mainViewModel.getUser(getuser)
//                }
//            }
//        }
//    }

    private fun fetchDataAllNowPlaying() {
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