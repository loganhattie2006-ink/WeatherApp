package com.example.weatherapp.Data.dao

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val location: Location,
    val current: Current,
    val forecast: Forecast
)

data class Location(
    val name: String,
    val region: String
)

data class Current(
    @SerializedName("temp_c") val tempC: Double,
    @SerializedName("feelslike_c") val feelsLikeC: Double,
    @SerializedName("wind_kph") val windKph: Double,
    @SerializedName("wind_dir") val windDir: String,
    val humidity: Int,
    val condition: Condition
)

data class Condition(
    val text: String,
    val icon: String
)

data class Forecast(
    @SerializedName("forecastday") val forecastDay: List<ForecastDay>
)

data class ForecastDay(
    val date: String,  // yyyy-mm-dd
    val day: Day
)

data class Day(
    @SerializedName("maxtemp_c") val maxTempC: Double,
    @SerializedName("mintemp_c") val minTempC: Double,
    @SerializedName("maxwind_kph") val maxWindKph: Double,
    @SerializedName("avghumidity") val avgHumidity: Double,
    @SerializedName("daily_chance_of_rain") val chanceOfRain: Int,
    @SerializedName("totalprecip_mm") val totalPrecipMm: Double,
    val condition: Condition
)