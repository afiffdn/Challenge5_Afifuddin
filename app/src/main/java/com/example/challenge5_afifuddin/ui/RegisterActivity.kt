package com.example.challenge5_afifuddin.ui


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.challenge5_afifuddin.R
import com.example.challenge5_afifuddin.databinding.ActivityRegisterBinding
import com.example.challenge5_afifuddin.model.User
import com.example.challenge5_afifuddin.ui.theme.appTheme
import com.example.challenge5_afifuddin.ui.theme.colorMain
import com.example.challenge5_afifuddin.ui.theme.white
import com.example.challenge5_afifuddin.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : ComponentActivity() {
    lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        setContent {
            appTheme {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Register()
                }

            }
        }
    }

    // tes ci

    @Composable

    fun Register() {
        Box(
            modifier = Modifier
                .background(white)
                .fillMaxSize()
        ) {
            RegistItems()

        }
    }

    @Composable
    fun RegistItems() {
        val username = remember {
            mutableStateOf("")
        }
        val email = remember {
            mutableStateOf("")
        }
        val password = remember {
            mutableStateOf("")
        }
        val repassword = remember {
            mutableStateOf("")
        }
        val passwordVisibility = remember { mutableStateOf(false) }
        val confirmPasswordVisibility = remember { mutableStateOf(false) }
        val context = LocalContext.current
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        )
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .padding(10.dp)
            ) {
                Text(
                    text = "REGISTER",
                    style = MaterialTheme.typography.h1
                )
                Spacer(modifier = Modifier.padding(20.dp))
                Icon(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "register",
                    tint = colorMain
                )
                Spacer(modifier = Modifier.padding(20.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(
                        value = username.value, onValueChange = { username.value = it },
                        label = { Text(text = "Username") },
                        placeholder = { Text(text = "Input Username") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(8.8f)
                    )
                    OutlinedTextField(
                        value = email.value,
                        onValueChange = { email.value = it },
                        label = { Text(text = "Email") },
                        placeholder = { Text(text = "Input Email") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(8.8f)
                    )
                    OutlinedTextField(
                        value = password.value,
                        onValueChange = { password.value = it },
                        label = { Text(text = "Password") },
                        visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisibility.value = !passwordVisibility.value
                            })
                            {
                                Icon(
                                    imageVector = if (passwordVisibility.value)
                                        Icons.Filled.Visibility
                                    else Icons.Filled.VisibilityOff, ""
                                )

                            }
                        },
                        placeholder = { Text(text = "Input Password") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(8.8f)
                    )
                    OutlinedTextField(
                        value = repassword.value,
                        onValueChange = { repassword.value = it },
                        label = { Text(text = "repassword") },
                        visualTransformation = if (confirmPasswordVisibility.value)
                            VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = {
                                confirmPasswordVisibility.value = !confirmPasswordVisibility.value
                            }) {
                                Icon(
                                    imageVector = if (confirmPasswordVisibility.value)
                                        Icons.Filled.Visibility
                                    else
                                        Icons.Filled.VisibilityOff, ""
                                )
                            }
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(8.8f)
                    )

                    Spacer(modifier = Modifier.padding(20.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorMain),
                        onClick = {
                            when {
                                username.value == "" || email.value == "" || password.value == "" || repassword.value == ""
                                -> {
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "data tidak boleh kosong",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                                password.value != repassword.value -> {
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "password tidak sama",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                else -> {
                                    val user = User(
                                        null,
                                        username.value,
                                        email.value,
                                        password.value,
                                        null,
                                        "no image"
                                    )
                                    viewModel.insertUser(user)
                                        Toast.makeText(
                                            this@RegisterActivity,
                                            "Register success",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        startActivity(
                                            Intent(
                                                this@RegisterActivity,
                                                LoginActivity::class.java
                                            )
                                        )


                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp)
                    ) {
                        Text(text = "Regist", style = MaterialTheme.typography.h3)

                    }

                }

            }

        }
    }
}


//
//        binding.btnDaftar.setOnClickListener {
//            val username = binding.etUsername.text.toString()
//            val email = binding.etEmail.text.toString()
//            val password = binding.etPassword.text.toString()
//            val repassword = binding.etRepassword.text.toString()
//            when {
//                username.isEmpty() -> {
//                    binding.etUsername.error = " input username"
//                }
//                email.isEmpty() -> {
//                    binding.etEmail.error = " input email"
//                }
//                password.isEmpty() -> {
//                    binding.etPassword.error = " input password"
//                }
//                repassword.isEmpty() -> {
//                    binding.etRepassword.error = " input repassword"
//                }
//                password.lowercase() != repassword.lowercase() ->{
//                    Toast.makeText(this, "wrong repassword", Toast.LENGTH_SHORT).show()
//                }
//                else -> {
//                    val obejctUser = User(null, username, email, password,null,"no_image")
//                    viewModel.insertUser(obejctUser)
//                    Toast.makeText(this, "sukses add ${username}", Toast.LENGTH_SHORT).show()
//                    startActivity(Intent(this,LoginActivity::class.java))
//
//                }
//            }
//
//        }
//    }
//
//}