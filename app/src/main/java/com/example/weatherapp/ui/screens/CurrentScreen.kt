//Displays the weather information for the current day

package com.example.weatherapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.weatherapp.Data.dao.WeatherResponse
import kotlin.math.roundToInt

@Composable
fun CurrentWeatherScreen(innerPadding: PaddingValues, weather: WeatherResponse?) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(innerPadding)
    ) {
        if (weather == null) { // the API call hasn't finished yet, so there is no data to show
            Text("Loading...")
        } else {
            val current = weather.current // holds the temp, wind and condition for right now

            AsyncImage( // loads the icon from the web, the API's icon links are missing the https:
                model = "https:${current.condition.icon}",
                contentDescription = current.condition.text,
                modifier = Modifier.size(96.dp)
            )

            Text(current.condition.text, style = MaterialTheme.typography.titleLarge)

            Text("${current.tempC.roundToInt()}°C", style = MaterialTheme.typography.displayLarge)

            Spacer(modifier = Modifier.height(8.dp))

            Text("Feels like ${current.feelsLikeC.roundToInt()}°C")

            Text("Wind ${current.windDir} ${current.windKph.roundToInt()} kph")

            val today = weather.forecast.forecastDay.firstOrNull()?.day // today's forecast holds the rain chance and amount

            if (today != null && today.chanceOfRain > 0) {
                val rainText = if (today.totalPrecipMm > 0.0) {
                    "Chance of rain ${today.chanceOfRain}%. Amount ${today.totalPrecipMm}mm."
                } else {
                    "Chance of rain ${today.chanceOfRain}%."
                }
                Text(rainText)
            }

            Text("Humidity ${current.humidity}%")
        }
    }
}