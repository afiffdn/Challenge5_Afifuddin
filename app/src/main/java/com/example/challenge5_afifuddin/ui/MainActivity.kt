package com.example.challenge5_afifuddin.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.challenge5_afifuddin.R
import com.example.challenge5_afifuddin.adapter.NowMovieShowingAdapter
import com.example.challenge5_afifuddin.adapter.TopRatedAdapter
import com.example.challenge5_afifuddin.service.ApiClient
import com.example.challenge5_afifuddin.databinding.ActivityMainBinding
import com.example.challenge5_afifuddin.datastore.DatastoreManager
import com.example.challenge5_afifuddin.room.Database
import com.example.challenge5_afifuddin.model_movies_now_showing.GetAllMovieNowShowing
import com.example.challenge5_afifuddin.model_movies_top_rated.GetTopRated
import com.example.challenge5_afifuddin.viewmodel.MainViewModel
import com.example.challenge5_afifuddin.viewmodel.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var dB: Database? = null
    lateinit var mainViewModel: MainViewModel
    lateinit var datastore: DatastoreManager

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        datastore = DatastoreManager(this)
        mainViewModel = ViewModelProvider(this, ViewModelFactory(datastore))[MainViewModel::class.java]
        dB = Database.getInstance(this)
        mainViewModel.getData().observe(this){
            binding.tvWelcome.text = getString(R.string.welcome_username, it.username)
        }

        //   mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //   dB = Database.getInstance(this)


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