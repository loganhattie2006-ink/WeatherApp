package com.example.weatherapp.models

import androidx.annotation.DrawableRes

data class WeatherForecast(
    val date: String,
    val highTemp: Int,
    val lowTemp: Int,
    val humidity: Int,
    val rainChance: Int,
    val rainAmount: Double? = null, // Makes the amount optional incase theres none, or cant find an amount provided
    val windSpeed: Int,
    val condition: String,
    @DrawableRes val iconRes: Int
)
