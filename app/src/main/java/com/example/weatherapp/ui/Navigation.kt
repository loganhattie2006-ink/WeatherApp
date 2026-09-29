package com.example.weatherapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.ui.screens.CurrentWeatherScreen
import com.example.weatherapp.ui.screens.ForecastScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(){

    val navController = rememberNavController()
    var selectedIndex by remember { mutableIntStateOf(0) } // default set to 0
    Scaffold(

        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),

                title = {
                    when (selectedIndex){ // checks the selected index to change the top bars content
                        0 -> Text("Todays Weather")
                        1-> Text("6 Day Forecast")
                        else -> Text("Weather")
                    }
                }
            )

        },
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets)
            {
                 NavigationBarItem( // Button in the nav bar for the Daily weather screen
                     icon = { Icon(imageVector = Icons.Default.WbSunny, contentDescription = "Daily Button") },
                     label = { Text("Daily") }, // label under button
                     selected = selectedIndex == 0, // this is how it tells the screens apart
                     onClick = {
                         selectedIndex = 0
                         navController.navigate(route="CurrentScreen"){ // sets the screen to the Daily weather, see the route in NavHost below
                             popUpTo(navController.graph.findStartDestination().id) {
                                 saveState = true
                             }
                             launchSingleTop = true
                             restoreState = true
                         }
                     }

                 )
                NavigationBarItem( // Button in the nav bar for the forecast
                    icon = { Icon(imageVector = Icons.Default.WbCloudy, contentDescription = "Forecast button") },
                    label = { Text("Forecast") },
                    selected = selectedIndex == 1,
                    onClick = {
                        selectedIndex = 1
                        navController.navigate(route="ForecastScreen"){
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }

                )
            }
        }
    )


    { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "CurrentScreen", //defaults to the daily weather
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = "CurrentScreen") { // When its daily weather it runs the function for the daily weather screen
                CurrentWeatherScreen(innerPadding)
            }
            composable(route = "ForecastScreen") { // Same but for the forecast screen
                ForecastScreen(innerPadding)
            }
        }

    }



}