package com.example.challenge5_afifuddin.ui//package com.example.challenge5_afifuddin


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.challenge5_afifuddin.R
import com.example.challenge5_afifuddin.ui.theme.appTheme
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Surface(
                color = MaterialTheme.colors.background,
                modifier = Modifier.fillMaxWidth()
            ) {

            }
            appTheme {
                SplashScreen()
            }
        }
    }

    @Composable
    fun SplashScreen(){
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
        LaunchedEffect(key1 = true){
        scale.animateTo(
            targetValue = 0.3f,
            animationSpec = tween(
                durationMillis = 900,
                easing = { OvershootInterpolator(2f).getInterpolation(it)}
            )
        )
            delay(200L)
            startActivity(Intent(this@SplashActivity,LoginActivity::class.java))
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize())
        {
            Image(
                painter = painterResource(id = R.drawable.ic_moviedb), contentDescription ="logo",
            modifier = Modifier
                .width(700.dp)
                .height(700.dp)
                .scale(scale.value))
        }
    }


}
