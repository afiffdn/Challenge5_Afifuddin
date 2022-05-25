package com.example.challenge5_afifuddin.ui


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.challenge5_afifuddin.databinding.ActivityRegisterBinding
import com.example.challenge5_afifuddin.model.User
import com.example.challenge5_afifuddin.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {
    lateinit var binding:ActivityRegisterBinding
    private val viewModel : RegisterViewModel by viewModel()
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
                    val obejctUser = User(null, username, email, password,null,"no_image")
                    viewModel.insertUser(obejctUser)
                    Toast.makeText(this, "sukses add ${username}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,LoginActivity::class.java))

                }
            }

        }
    }

}