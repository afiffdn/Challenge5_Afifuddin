package com.example.challenge5_afifuddin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.challenge5_afifuddin.databinding.ActivityLoginBinding
import com.example.challenge5_afifuddin.datastore.DatastoreManager
import com.example.challenge5_afifuddin.login.database_login.Database
import com.example.challenge5_afifuddin.viewmodel.MainViewModel
import com.example.challenge5_afifuddin.viewmodel.ViewModelFactory
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private var dB: Database? = null
    lateinit var datastore: DatastoreManager
    lateinit var viewmodel : MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        dB = Database.getInstance(this)
        datastore = DatastoreManager(this)
        viewmodel = ViewModelProvider(this,ViewModelFactory(datastore))[MainViewModel::class.java]


        viewmodel.apply {
            getData().observe(this@LoginActivity){
                if (it.id_user != -1)
                {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                }
            }
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
                if (login != null) {
                    viewmodel.setDataUser(login)
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