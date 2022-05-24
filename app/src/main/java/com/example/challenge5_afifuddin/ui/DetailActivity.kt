package com.example.challenge5_afifuddin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.challenge5_afifuddin.R
import com.example.challenge5_afifuddin.databinding.ActivityDetailBinding
import com.example.challenge5_afifuddin.datastore.DatastoreManager
import com.example.challenge5_afifuddin.model.Favorite
import com.example.challenge5_afifuddin.room.Database
import com.example.challenge5_afifuddin.viewmodel.DetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private var dB: Database? = null
    private lateinit var datastore: DatastoreManager
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val id = intent.getIntExtra("id", 0)
        Log.d("tesIntent", id.toString())
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.getDetails(id)
        viewModel.movieDetail.observe(this) { data ->
            binding.apply {
                Glide.with(this@DetailActivity)
                    .load("https://image.tmdb.org/t/p/w500${data.posterPath}")
                    .into(ivMovie)
                tvJudul.text = data.title
                tahun.text = "(${data.releaseDate})"
                isiOverview.text = data.overview


                lifecycleScope.launch(Dispatchers.IO) {
                    val favorite = dB?.favorite()?.cekFavorite(id, data.title)
                    if (favorite == true) {
                        val dataFavorite = dB?.favorite()?.getFavoriteByIdAndCountry(id, data.title)
                        if (dataFavorite!!.movie_name == data.title) {
                            runBlocking(Dispatchers.Main) {
                                btnFav.setImageResource(R.drawable.ic_baseline_favorite_24)
                            }
                        }
                    }
                    else {
                        btnFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    }


                }
                btnFav.setOnClickListener {
                    lifecycleScope.launch(Dispatchers.IO){
                        val cekFavorite = dB?.favorite()?.cekFavorite(id,data.title )
                        if (cekFavorite == true){
                            val getDataFavorite = dB?.favorite()?.getFavoriteByIdAndCountry(id, data.title)
                            if (getDataFavorite!!.movie_name == data.title){
                                val favorite = Favorite(
                                    getDataFavorite.id_favorite,
                                    getDataFavorite.id_user,
                                    getDataFavorite.movie_name,
                                    getDataFavorite.tahun,
                                    getDataFavorite.deskr,
                                    getDataFavorite.image
                                )
                                val deleteFavorite = dB?.favorite()?.deleteFavorite(favorite)
                                if (deleteFavorite != 0){
                                    runBlocking(Dispatchers.Main){
                                        Toast.makeText(this@DetailActivity, "${data.title} Tidak Favorite", Toast.LENGTH_SHORT).show()
                                        btnFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                                    }
                                }
                            }
                        } else {
                            val favorite = Favorite(
                                null,
                                id,
                                data.title,
                                data.releaseDate,
                                data.overview,
                                data.posterPath
                            )
                            val tambahFavorite = dB?.favorite()?.addFavorite(favorite)
                            if (tambahFavorite != 0.toLong()){
                                runBlocking(Dispatchers.Main){
                                    Toast.makeText(this@DetailActivity, "${data.title} Favorite", Toast.LENGTH_SHORT).show()
                                    btnFav.setImageResource(R.drawable.ic_baseline_favorite_24)
                                }
                            }
                        }
                    }

                }
                }


            }
        }

    }