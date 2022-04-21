package com.example.challenge5_afifuddin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.challenge5_afifuddin.databinding.ActivityRegisterBinding
import com.example.challenge5_afifuddin.login.database_login.Database
import com.example.challenge5_afifuddin.login.database_login.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RegisterActivity : AppCompatActivity() {
    lateinit var binding:ActivityRegisterBinding
    var dB: Database? = null

    companion object {
        const val FILE = "kotlinsharedpreferences"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btnDaftar.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val repassword = binding.etRepassword.text.toString()
            when {
                username.isEmpty() -> {
                    binding.etUsername.error = " input username"
                }
                email.isEmpty() -> {
                    binding.etEmail.error = " input email"
                }
                password.isEmpty() -> {
                    binding.etPassword.error = " input password"
                }
                repassword.isEmpty() -> {
                    binding.etRepassword.error = " input repassword"
                }
                password.lowercase() != repassword.lowercase() ->{
                    Toast.makeText(this, "wrong repassword", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    register()
                }
            }

        }
    }

    private fun register() {
        dB = Database.getInstance(this)
        val username = binding.etUsername.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val repassword = binding.etRepassword.text.toString()
        val obejctUser = User(null, username, email, password, repassword,null,null,null)
        val preferences = this.getSharedPreferences(FILE, Context.MODE_PRIVATE)

        lifecycleScope.launch(Dispatchers.IO) {
            val result = dB?.user()?.insertUser(obejctUser)
            runBlocking(Dispatchers.Main) {
                if (result != 0.toLong()) {
                    val editor: SharedPreferences.Editor = preferences.edit()
                    editor.putString("email", email)
                    editor.putString("username",username)
                    editor.apply()
                    Toast.makeText(
                        this@RegisterActivity,
                        "Success add ${obejctUser.username}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "failed add ${obejctUser.username}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }
}