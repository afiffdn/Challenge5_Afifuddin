package com.example.challenge5_afifuddin.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.challenge5_afifuddin.databinding.ActivityLoginBinding
import com.example.challenge5_afifuddin.datastore.DatastoreManager
import com.example.challenge5_afifuddin.room.Database
import com.example.challenge5_afifuddin.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private var dB: Database? = null
    lateinit var datastore: DatastoreManager
    private val viewmodel : LoginViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        datastore = DatastoreManager(this)
//        viewmodel = ViewModelProvider(this,ViewModelFactory(datastore))[MainViewModel::class.java]

//        viewmodel.apply {
//            getData().observe(this@LoginActivity){
//                if (it.id_user != -1)
//                {
//                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
//                }
//            }
//        }

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
                    viewmodel.checkUser(email,password)
                    startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                }
            }
        }
    }
}