package com.example.challenge5_afifuddin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.challenge5_afifuddin.databinding.ActivityUserBinding
import com.example.challenge5_afifuddin.login.database_login.Database
import com.example.challenge5_afifuddin.login.database_login.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUserBinding
    private var dB : Database? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUserBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        fetchData()
        binding.btnLogout.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

    }

    private fun fetchData() {
        dB = Database.getInstance(this)
        val objectuser = intent.getParcelableExtra<User>("user")
        if (objectuser != null) {
            binding.etEditNamaLengkap.setText(objectuser.namaLengkap)
            binding.etEditUsername.setText(objectuser.username)
            binding.etTanggalLahir.setText(objectuser.tanggalLahir)
            binding.etEditAlamat.setText(objectuser.alamat)

            binding.btnUpdate.setOnClickListener {
                objectuser.namaLengkap = binding.etEditUsername.text.toString()
                objectuser.username = binding.etEditUsername.text.toString()
                objectuser.tanggalLahir = binding.etTanggalLahir.text.toString()
                objectuser.alamat = binding.etEditAlamat.text.toString()

                lifecycleScope.launch(Dispatchers.IO) {
                    val result = dB?.user()?.updateUser(objectuser)

                    runBlocking(Dispatchers.Main) {
                        if (result != 0) {
                            Toast.makeText(
                                this@UserActivity,
                                "Success change ${objectuser.namaLengkap}",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                this@UserActivity,
                                "Gagal mengubah ${objectuser.namaLengkap}",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        finish()
                    }
                }
            }
        }
    }
}