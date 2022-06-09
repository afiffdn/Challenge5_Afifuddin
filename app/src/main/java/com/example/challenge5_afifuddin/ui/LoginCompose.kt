package com.example.challenge5_afifuddin.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.challenge5_afifuddin.ui.theme.colorMain
import com.example.challenge5_afifuddin.ui.theme.white
import com.example.challenge5_afifuddin.R
import com.example.challenge5_afifuddin.model.User
import com.example.challenge5_afifuddin.ui.theme.appTheme

@Composable

fun Login() {
    Box(
        modifier = Modifier
            .background(white)
            .fillMaxSize()
    ) {
        LoginItems()

    }
}

@Composable
fun LoginItems() {
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
                text = "LOGIN",
                style = MaterialTheme.typography.h1
            )
            Spacer(modifier = Modifier.padding(20.dp))
            Icon(
                painter = painterResource(id = R.drawable.user ),
                contentDescription = "login",
                tint = colorMain
            )
            Spacer(modifier = Modifier.padding(20.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                Spacer(modifier = Modifier.padding(20.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorMain),
                    onClick = {
                        when {
                            username.value == "" || email.value == "" || password.value == "" || repassword.value == ""
                            -> {
                                Toast.makeText(context, "data tidak boleh kosong", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            password.value != repassword.value -> {
                                Toast.makeText(
                                    context,
                                    "password tidak sama",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else ->{
                                val user = User(
                                    null,
                                    username.value,
                                    email.value,
                                    password.value,
                                    null,
                                    "no image"
                                )

                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp)
                ) {
                    Text(text = "Login", style = MaterialTheme.typography.h3)

                }

            }

        }

        Text( color = colorMain, text = "Create an account", style = MaterialTheme.typography.h2,
            modifier = Modifier.padding(10.dp).clickable (onClick = {

            }) )


    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegiterPreview() {
    appTheme {
        LoginItems()
    }
}