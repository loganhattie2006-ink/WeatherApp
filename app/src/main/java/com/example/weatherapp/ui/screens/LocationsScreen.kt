package com.example.weatherapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.weatherapp.Data.dao.SearchResult

@Composable
fun LocationsScreen(
    innerPadding: PaddingValues,
    myLocationName: String?,
    searchResults: List<SearchResult>,
    savedLocations: List<SearchResult>,
    onSelectMyLocation: () -> Unit,
    onSelectSaved: (SearchResult) -> Unit,
    onSearch: (String) -> Unit,
    onAdd: (SearchResult) -> Unit,
    onRequestPermission: () -> Unit
) {
    var query by remember { mutableStateOf("") } // what's typed in the box, only this screen needs it

    LazyColumn(modifier = Modifier.padding(innerPadding)) {

        item { Text("My Location", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(16.dp)) }

        item {
            if (myLocationName != null) {
                Text(
                    text = myLocationName,
                    modifier = Modifier.fillMaxWidth().clickable { onSelectMyLocation() }.padding(16.dp)
                )
            } else {
                Button(onClick = onRequestPermission, modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text("Allow location access")
                }
            }
        }

        item { HorizontalDivider() }

        item { Text("Add a Canadian City", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(16.dp)) }

        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    label = { Text("City name") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search), // keyboard shows a search key
                    keyboardActions = KeyboardActions(onSearch = { onSearch(query) }),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { onSearch(query) }) { Text("Search") }
            }
        }

        items(searchResults) { place ->
            val alreadySaved = savedLocations.any { it.id == place.id }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Text("${place.name}, ${place.region}", modifier = Modifier.weight(1f))
                Button(onClick = { onAdd(place) }, enabled = !alreadySaved) {
                    Text(if (alreadySaved) "Saved" else "Add")
                }
            }
        }

        item { HorizontalDivider(modifier = Modifier.padding(top = 8.dp)) }

        item { Text("Saved Locations", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(16.dp)) }

        if (savedLocations.isEmpty()) {
            item { Text("No saved locations yet", modifier = Modifier.padding(horizontal = 16.dp)) }
        }

        items(savedLocations) { place ->
            Text(
                text = "${place.name}, ${place.region}",
                modifier = Modifier.fillMaxWidth().clickable { onSelectSaved(place) }.padding(16.dp)
            )
            HorizontalDivider()
        }
    }
}