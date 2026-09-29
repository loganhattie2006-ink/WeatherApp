

// Display the forecast for the upcoming week (5- 7 days) using-
// a lazy list for better performance and potential expansion
package com.example.weatherapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.models.WeatherForecast

@Composable
fun ForecastScreen(innerPadding: PaddingValues) {

    val forecastList = listOf(  // list of the forecasts
        WeatherForecast(
            date = "Wed, Sep 30",
            highTemp = 18,
            lowTemp = 10,
            humidity = 89,
            rainChance = 17,
            windSpeed = 4,
            condition = "Cloudy",
            iconRes = R.drawable.cloudy
        ),
        WeatherForecast(
            date = "Thu, Oct 1",
            highTemp = 21,
            lowTemp = 14,
            humidity = 79,
            rainChance = 18,
            windSpeed = 12,
            condition = "Partially Cloudy",
            iconRes = R.drawable.partial_cloudy
        ),
        WeatherForecast(
            date = "Fri, Oct 2",
            highTemp = 23,
            lowTemp = 12,
            humidity = 82,
            rainChance = 22,
            windSpeed = 10,
            condition = "Cloudy",
            iconRes = R.drawable.cloudy
        ),
        WeatherForecast(
            date = "Sat, Oct 3",
            highTemp = 19,
            lowTemp = 5,
            humidity = 71,
            rainChance = 24,
            windSpeed = 14,
            condition = "Sunny",
            iconRes = R.drawable.sunny
        ),
        WeatherForecast(
            date = "Sun, Oct 4",
            highTemp = 17,
            lowTemp = 10,
            humidity = 62,
            rainChance = 12,
            windSpeed = 9,
            condition = "Sunny",
            iconRes = R.drawable.sunny
        ),
        WeatherForecast(
            date = "Mon, Oct 5",
            highTemp = 17,
            lowTemp = 5,
            humidity = 100,
            rainChance = 45,
            windSpeed = 14,
            condition = "Rain",
            iconRes = R.drawable.rain
        )
    )

    LazyColumn(modifier = Modifier.padding(innerPadding)) {
        items(forecastList) { day ->  // feeds the list to the column to display

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 24.dp)
            ) {
                Text(day.date, style = MaterialTheme.typography.titleMedium)

                Spacer(modifier = Modifier.height(8.dp))

                Image( // image and its modifications
                    painter = painterResource(day.iconRes),
                    contentDescription = day.date,
                    modifier = Modifier.size(48.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(day.condition)

                Text("${day.highTemp}°C High  ${day.lowTemp}°C Low")

                if (day.rainChance > 0) { // this checks for the rain chance to see if anything rain related is displayed
                    val rainText = if (day.rainAmount != null) { // checks if an amount is forecasted and displays it if so
                        "Chance of rain ${day.rainChance}%. Amount ${day.rainAmount}mm."
                    } else { // if no amounts forecasted it just displays the chance
                        "Chance of rain ${day.rainChance}%."
                    }

                    Text(
                        text = rainText,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                }

                Text(
                    text = "Maximum winds ${day.windSpeed}kph. Humidity ${day.humidity}%.",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }

            HorizontalDivider() // line at the end to separate the entries
        }
    }
}