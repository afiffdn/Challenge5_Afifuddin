package com.example.challenge5_afifuddin.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.challenge5_afifuddin.R

val gothicA1 = FontFamily(
    listOf(
        Font(R.font.gothic1_regular, FontWeight.Normal),
        Font(R.font.gothic1_medium, FontWeight.Medium),
        Font(R.font.gothic1_semibold, FontWeight.SemiBold),
        Font(R.font.gothic1_bold, FontWeight.Bold),
        Font(R.font.gothic1_black, FontWeight.Black),
    )
)

val Typography = androidx.compose.material.Typography(
    h1 = TextStyle(
        color = colorMain,
        fontFamily = gothicA1,
        fontWeight = FontWeight.Black,
        fontSize = 25.sp
    ),
    h2 = TextStyle(
        color = Color.White,
        fontFamily = gothicA1,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp
    ),
    h3 = TextStyle(
        color = white,
        fontFamily = gothicA1,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    )


)