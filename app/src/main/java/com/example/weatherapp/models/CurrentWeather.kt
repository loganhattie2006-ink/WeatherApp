package com.example.weatherapp.models

import androidx.annotation.DrawableRes

data class CurrentWeather(
    val location: String,
    val temperature: Int,
    val feelsLike: Int,
    val windDirection: String,
    val windSpeed: Int,
    val condition: String,      // like overcast, cloudy, sunny etc
    @DrawableRes val iconRes: Int
)
