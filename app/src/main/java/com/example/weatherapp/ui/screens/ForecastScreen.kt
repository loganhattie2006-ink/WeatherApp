// Display the forecast for the upcoming week (5- 7 days) using-
// a lazy list for better performance and potential expansion
package com.example.weatherapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.weatherapp.Data.dao.ForecastDay
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun ForecastScreen(innerPadding: PaddingValues, forecastList: List<ForecastDay>) {

    if (forecastList.isEmpty()) { // nothing has loaded yet, so show a message instead of a blank screen
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text("Loading forecast...")
        }
        return // stops here so the list code below only runs when there is data
    }

    LazyColumn(modifier = Modifier.padding(innerPadding)) {
        items(forecastList) { forecastDay ->  // feeds the list to the column to display

            val day = forecastDay.day // the day object holds the temps, rain, wind etc.

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 24.dp)
            ) {
                // the API gives "2026-11-15", this turns it into "Sun, Nov 15"
                Text(
                    LocalDate.parse(forecastDay.date).format(DateTimeFormatter.ofPattern("EEE, MMM d")),
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                AsyncImage( // loads the icon from the web, the API's icon links are missing the https:
                    model = "https:${day.condition.icon}",
                    contentDescription = day.condition.text,
                    modifier = Modifier.size(48.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(day.condition.text)

                Text("${day.maxTempC.roundToInt()}°C High  ${day.minTempC.roundToInt()}°C Low")

                if (day.chanceOfRain > 0) { // this checks for the rain chance to see if anything rain related is displayed
                    val rainText = if (day.totalPrecipMm > 0.0) { // checks if an amount is forecasted and displays it if so
                        "Chance of rain ${day.chanceOfRain}%. Amount ${day.totalPrecipMm}mm."
                    } else { // if no amounts forecasted it just displays the chance
                        "Chance of rain ${day.chanceOfRain}%."
                    }

                    Text(
                        text = rainText,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                }

                Text(
                    text = "Maximum winds ${day.maxWindKph.roundToInt()}kph. Humidity ${day.avgHumidity.roundToInt()}%.",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }

            HorizontalDivider() // line at the end to separate the entries
        }
    }
}