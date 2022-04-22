package com.example.challenge5_afifuddin

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.challenge5_afifuddin.databinding.ActivityUserBinding
import com.example.challenge5_afifuddin.login.database_login.Database
import com.example.challenge5_afifuddin.login.database_login.User
import com.example.challenge5_afifuddin.viewmodel.ProfilViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    private var dB: Database? = null
    private lateinit var profileViewModel: ProfilViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUserBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        fetchData()
        binding.btnLogout.setOnClickListener {
            val sharedPreferences =
                this.getSharedPreferences(LoginActivity.EMAIL, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }

    @SuppressLint("CommitPrefEdits")
    private fun fetchData() {
        dB = Database.getInstance(this)
        val sharedPreferences =
            this.getSharedPreferences(RegisterActivity.FILE, Context.MODE_PRIVATE)
        profileViewModel = ViewModelProvider(this).get(ProfilViewModel::class.java)
        val username = sharedPreferences.getString("username", "default_user")
        if (username != null) {
            getData(username)
        }
        var id = 1
        profileViewModel.userData.observe(this) {
            id = it.id_user!!
            binding.etEditUsername.setText(it.username)
            binding.etEditEmail.setText(it.email)
            binding.etEditPassword.setText(it.password)
            binding.etEditNamaLengkap.setText(it.namaLengkap)
            binding.etTanggalLahir.setText(it.tanggalLahir)
            binding.etEditAlamat.setText(it.alamat)
        }
        binding.btnUpdate.setOnClickListener {
            when {
                binding.etEditNamaLengkap.text.isNullOrEmpty()
                        || binding.etTanggalLahir.text.isNullOrEmpty()
                        || binding.etEditAlamat.text.isNullOrEmpty() -> {
                    Toast.makeText(this, "incomplete data", Toast.LENGTH_SHORT).show()
                }else->{
                val user = User(
                    id,
                    binding.etEditUsername.text.toString(),
                    binding.etEditEmail.text.toString(),
                    binding.etEditPassword.text.toString(),
                    null,
                    binding.etEditNamaLengkap.text.toString(),
                    binding.etTanggalLahir.text.toString(),
                    binding.etEditAlamat.text.toString()
                )
                lifecycleScope.launch(Dispatchers.IO){
                    val result =dB?.user()?.updateUser(user)
                    runBlocking(Dispatchers.Main) {
                        if (result != 0){
                            Toast.makeText(this@UserActivity, "Data ${binding.etEditNamaLengkap.text} Berhasil Disimpan ", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@UserActivity, MainActivity::class.java))
                        } else {
                            Toast.makeText(
                                this@UserActivity,
                                "Data Gagal Disimpan",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                }
            }
        }


    }

    private fun getData(username: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val user = dB?.user()?.getUser(username)
            runBlocking(Dispatchers.Main) {
                if (user != null) {
                    profileViewModel.dataUser(user)
                }
            }
        }
    }
}