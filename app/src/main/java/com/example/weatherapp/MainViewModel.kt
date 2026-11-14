package com.example.weatherapp

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.BuildConfig.API_KEY
import com.example.weatherapp.Data.dao.WeatherResponse
import com.example.weatherapp.Data.WeatherAPI
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

class MainViewModel : ViewModel() {

    var weather by mutableStateOf<WeatherResponse?>(null)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    private val api = Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherAPI::class.java)

    fun loadWeather(location: String) {
        viewModelScope.launch {
            try {
                weather = api.getForecast(API_KEY, location, 3) // API key is stored in local properties
                errorMessage = null
            } catch (e: Exception) {
                errorMessage = "Couldn't load weather: ${e.message}"
                android.util.Log.e("WeatherApp", "loadWeather failed", e)
            }
        }
    }

    @SuppressLint("MissingPermission") // only called after the permission result says it was granted
    fun loadDeviceLocationWeather(client: FusedLocationProviderClient) {
        client.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                if (location != null) {
                    loadWeather("${location.latitude},${location.longitude}") // WeatherAPI accepts "lat,lon" as the location
                } else {
                    loadWeather("Halifax") // no fix available, so fall back to halifax as a default
                }
            }
            .addOnFailureListener {
                loadWeather("Halifax")
            }
    }
}