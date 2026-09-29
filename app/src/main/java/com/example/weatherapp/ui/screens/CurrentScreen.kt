

//Displays the weather information for the current day

package com.example.weatherapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weatherapp.models.CurrentWeather

@Composable
fun CurrentWeatherScreen(innerPadding: PaddingValues) {

    // placeholder info, i wanna try and expand the app to have location options so im storing location in the example data
    val weather = CurrentWeather(
        location = "Halifax, Nova Scotia",
        temperature = 18,
        feelsLike = 18,
        windDirection = "NW",
        windSpeed = 6,
        condition = "Showers",
        iconRes = com.example.weatherapp.R.drawable.rain
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Image(
            painter = painterResource(weather.iconRes),
            contentDescription = weather.condition
        )

        Text(text = weather.condition,
            style = MaterialTheme.typography.titleLarge)

        Text(text = "${weather.temperature}°C",
            style = MaterialTheme.typography.displayLarge)

        Spacer(modifier = Modifier.height(8.dp))

        Text("Feels like ${weather.feelsLike}°C")
        Text("Wind ${weather.windDirection} ${weather.windSpeed} kph")
    }
}