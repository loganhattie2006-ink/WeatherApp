package com.example.weatherapp.models

import androidx.annotation.DrawableRes

data class WeatherForecast(
    val date: String,           // maybe change to date?
    val highTemp: Int,
    val lowTemp: Int,
    val summary: String,        // desc like chance of rain 20%, maybe humidity here?
    @DrawableRes val iconRes: Int
)
