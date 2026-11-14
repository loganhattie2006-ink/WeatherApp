package com.example.weatherapp.Data

import com.example.weatherapp.Data.dao.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("v1/forecast.json")
    suspend fun getForecast(
        @Query("key") key: String,
        @Query("q") location: String,
        @Query("days") days: Int
    ): WeatherResponse
}