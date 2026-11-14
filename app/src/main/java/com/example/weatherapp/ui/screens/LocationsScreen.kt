package com.example.weatherapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LocationsScreen(
    innerPadding: PaddingValues,
    myLocationName: String?,
    savedLocations: List<String>,
    onSelectMyLocation: () -> Unit,
    onSelectSaved: (String) -> Unit,
    onRequestPermission: () -> Unit
) {
    LazyColumn(modifier = Modifier.padding(innerPadding)) {

        item { Text("My Location", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(16.dp)) }

        item {
            if (myLocationName != null) { // permission was granted and the device location has loaded
                Text(
                    text = myLocationName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectMyLocation() }
                        .padding(16.dp)
                )
            } else { // no device location yet, so offer to ask for permission
                Button(onClick = onRequestPermission, modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text("Allow location access")
                }
            }
        }

        item { HorizontalDivider() }

        item { Text("Saved Locations", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(16.dp)) }

        if (savedLocations.isEmpty()) {
            item { Text("No saved locations yet", modifier = Modifier.padding(horizontal = 16.dp)) }
        }

        items(savedLocations) { name ->
            Text(
                text = name,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectSaved(name) }
                    .padding(16.dp)
            )
            HorizontalDivider()
        }
    }
}