package com.example.challenge5_afifuddin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val preferences = this.getSharedPreferences(RegisterActivity.FILE, Context.MODE_PRIVATE)
        val email = preferences.getString("email","default")
        if (email != "default"){
            Handler().postDelayed({
                startActivity(Intent(this,MainActivity::class.java))
            },1000

            )

        }else{
            Handler().postDelayed(
                {
                    startActivity(Intent(this,LoginActivity::class.java)) },
                1000
            )
        }


    }

}