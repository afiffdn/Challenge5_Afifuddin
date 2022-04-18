package com.example.challenge5_afifuddin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.challenge5_afifuddin.databinding.ActivityLoginBinding
import com.example.challenge5_afifuddin.login.database_login.Database
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private var dB: Database? = null
    private lateinit var preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        dB = Database.getInstance(this)

        preferences = this.getSharedPreferences(RegisterActivity.EMAIL, Context.MODE_PRIVATE)
        val nama = preferences.getString(RegisterActivity.EMAIL, "default")
        if (nama != "default") {
            binding.etEmail.setText(nama)
        }

        binding.btnLogin.setOnClickListener {
            checkLogin()
        }
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }


    private fun checkLogin() {
        with(binding) {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            when {
                email.isEmpty() -> {
                    binding.etEmail.error = "Input Email"
                }
                password.isEmpty() -> {
                    binding.etPassword.error = "Input Password"
                }
                else -> {
                    chechUser()
                }
            }
        }
    }

    private fun chechUser() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        lifecycleScope.launch(Dispatchers.IO) {
            val login = dB?.user()?.checkUser(email, password)
            runBlocking(Dispatchers.Main) {
                if (login == true) {
                    Toast.makeText(this@LoginActivity, "Login Sukses", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                } else {
                    Toast.makeText(this@LoginActivity, "gagal login", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}