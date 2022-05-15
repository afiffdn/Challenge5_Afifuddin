package com.example.challenge5_afifuddin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.challenge5_afifuddin.adapter.FavoriteAdapter
import com.example.challenge5_afifuddin.databinding.ActivityFavoriteBinding
import com.example.challenge5_afifuddin.datastore.DatastoreManager
import com.example.challenge5_afifuddin.favorite.Favorite
import com.example.challenge5_afifuddin.login.database_login.Database
import com.example.challenge5_afifuddin.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FavoriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityFavoriteBinding
    private var dB: Database? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val id = intent.getIntExtra("id", 0)
        favorite(id)
    }

    private fun favorite(id: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            val dataFavorite = dB?.favorite()?.getFavorite(id)
            if (dataFavorite != null) {

            }
        }
    }

    private fun showList(list:List<Favorite> ) {
        val adapter = FavoriteAdapter(object : FavoriteAdapter.OnClickListener{
            override fun onClickItem(data: Favorite) {
                val toDetail = Intent(this@FavoriteActivity, DetailActivity::class.java).apply {
                    putExtra("name",data.movie_name)
                }
                startActivity(toDetail)

            }
        })
        adapter.submitData(list)
        binding.rvFavorite.adapter = adapter
    }

}
