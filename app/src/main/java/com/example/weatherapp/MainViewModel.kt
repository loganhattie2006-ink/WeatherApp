package com.example.weatherapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import com.example.weatherapp.Data.dao.SearchResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var weather by mutableStateOf<WeatherResponse?>(null)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    private val api = Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherAPI::class.java)



    var myLocationName by mutableStateOf<String?>(null)   // e.g. "Halifax", shown under My Location
        private set
    var myLocationQuery by mutableStateOf<String?>(null)  // the "lat,lon" so it can be reloaded later
        private set

    var searchResults by mutableStateOf<List<SearchResult>>(emptyList())
        private set

    private val prefs = application.getSharedPreferences("weather_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    val savedLocations = mutableStateListOf<SearchResult>()

    init {
        loadSavedLocations() // runs once when the ViewModel is created, i.e. at app start
    }

    private fun loadSavedLocations() {
        val json = prefs.getString("saved_locations", null) ?: return // nothing saved yet
        try {
            val type = object : TypeToken<List<SearchResult>>() {}.type
            val list: List<SearchResult> = gson.fromJson(json, type)
            savedLocations.addAll(list)
        } catch (e: Exception) {
            android.util.Log.e("WeatherApp", "couldn't read saved locations", e)
        }
    }

    private fun saveSavedLocations() {
        prefs.edit()
            .putString("saved_locations", gson.toJson(savedLocations.toList()))
            .apply()
    }

    fun searchCities(query: String) {
        if (query.isBlank()) return
        viewModelScope.launch {
            try {
                searchResults = api.searchLocations(API_KEY, query.trim())
                    .filter { it.country == "Canada" } // the endpoint searches worldwide, so keep only Canadian places as the reqs wanted
            } catch (e: Exception) {
                searchResults = emptyList()
                android.util.Log.e("WeatherApp", "searchCities failed", e)
            }
        }
    }

    fun addSavedLocation(place: SearchResult) {
        if (savedLocations.none { it.id == place.id }) {
            savedLocations.add(place)
            saveSavedLocations() // write the new list to storage
        }
    }




    fun loadWeather(location: String, isMyLocation: Boolean = false) {
        viewModelScope.launch {
            try {
                val result = api.getForecast(API_KEY, location, 3)
                weather = result
                if (isMyLocation) {
                    myLocationName = result.location.name
                    myLocationQuery = location
                }
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
                    loadWeather("${location.latitude},${location.longitude}", isMyLocation = true) // WeatherAPI accepts "lat,lon" as the location
                } else {
                    loadWeather("Halifax") // no fix available, so fall back to halifax as a default
                }
            }
            .addOnFailureListener {
                loadWeather("Halifax")
            }
    }
}