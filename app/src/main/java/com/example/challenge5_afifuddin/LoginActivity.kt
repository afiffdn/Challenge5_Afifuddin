package com.example.challenge5_afifuddin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isNotEmpty
import androidx.lifecycle.lifecycleScope
import com.example.challenge5_afifuddin.databinding.ActivityLoginBinding
import com.example.challenge5_afifuddin.login.database_login.Database
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private var dB: Database? = null
    companion object {
        const val EMAIL = "email"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        dB = Database.getInstance(this)

        val preferences = this.getSharedPreferences(RegisterActivity.FILE, Context.MODE_PRIVATE)
        val email = preferences.getString( "email","default")
        if (email != "default"){
            binding.etEmail.setText(email)
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
        val preferences = this.getSharedPreferences(EMAIL, Context.MODE_PRIVATE)
        lifecycleScope.launch(Dispatchers.IO) {
            val login = dB?.user()?.checkUser(email, password)
            runBlocking(Dispatchers.Main) {
                if (login == true) {
                    val editor : SharedPreferences.Editor = preferences.edit()
                    editor.putString("email",email)
                    editor.apply()
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