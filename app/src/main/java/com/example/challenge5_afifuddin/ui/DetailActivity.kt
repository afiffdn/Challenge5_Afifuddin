package com.example.challenge5_afifuddin.ui

import android.content.Intent
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
import com.example.challenge5_afifuddin.model.Detail
import com.example.challenge5_afifuddin.model.Favorite
import com.example.challenge5_afifuddin.room.Database
import com.example.challenge5_afifuddin.viewmodel.DetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        val data = intent.getParcelableExtra<Detail>("data")
        binding.apply {
            Glide.with(this@DetailActivity)
                .load("https://image.tmdb.org/t/p/w500${data?.poster}")
                .into(ivMovie)
            tvJudul.text = data?.tittle
            isiOverview.text = data?.overview
            tahun.text = data?.tahun
        }

    }

}