package com.example.challenge5_afifuddin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.challenge5_afifuddin.databinding.ActivityDetailBinding
import com.example.challenge5_afifuddin.viewmodel.GetDetailViewModel

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: GetDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val id = intent.getIntExtra("id", 0)
        Log.d("tesIntent", id.toString())
        viewModel = ViewModelProvider(this).get(GetDetailViewModel::class.java)
        viewModel.getDetails(id)
        viewModel.movieDetail.observe(this) {
            binding.apply {
                Glide.with(this@DetailActivity)
                    .load("https://image.tmdb.org/t/p/w500${it.posterPath}")
                    .into(ivMovie)
                tvJudul.text = it.title
                tahun.text = "(${it.releaseDate})"
                isiOverview.text = it.overview


            }
        }

    }
}