package com.filmdiscover

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.filmdiscover.ui.theme.FilmDiscoverTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.filmdiscover.data.Film
import com.filmdiscover.data.service
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FilmDiscoverTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    HomeScreen()
                }
            }
        }

        lifecycleScope.launchWhenResumed {
            try {
                val searchList = service.discoverList()
                Log.d("main activtiy", searchList.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

//data class Film(val title: String, val rating: Double)

val films = listOf<Film>(
    Film(title = "A home alone", rating = 4.35),
    Film(title = "A home alone 2", rating = 3.0),
    Film(title = "Movie name", rating = 5.2),
)

@Composable
fun HomeScreen() {
    // TODO: add debounce
    // remember keep the link between renders (like useMemo)
    val (searchText, setSearchText) = remember { mutableStateOf("") }
    val loading = false
    val error = false

    var errorAlert by remember { mutableStateOf(false) }

    // onDidMount send request to /discover

    Column {
        OutlinedTextField(value = searchText, onValueChange = setSearchText)
        Text(text = "Search text: ${searchText}", fontSize = 24.sp)
        if (loading) {
            CircularProgressIndicator()
        }
        if (!loading && searchText.isNotBlank() && films.isEmpty()) {
            Text("No results")
        }

        if (!loading && error && films.isEmpty()) {
            // TODO: show the error text instead
            Text("Something went wrong, please try again")
            // TODO: add onClick handler
            Button({ errorAlert = true }) {
                Text(text = "Refresh")
            }
        }

        if (errorAlert) {
            // TODO: provide real error text
            ErrorAlert(errorText = "Smth went wrong", closeModal = { errorAlert = false })
        }

        LazyColumn {
            items(films) {
                MessageCard(it)
            }
        }

    }
}

@Composable
fun MessageCard(film: Film) {
    val openDetails = {
        // do navigation
    }

    val addToFav = {
        // add to favs or remove from favs
    }

    // TODO: update View of Film card
    Row(modifier = Modifier.clickable { openDetails() }) {
        Text(text = "${film.title}, ")
        Text(text = "${film.rating}")
        // TODO: add svg icon
        Text(text = "  (Add to fav)", modifier = Modifier.clickable { addToFav() })
    }
}

@Composable
// Unit is void
fun ErrorAlert(errorText: String, closeModal: () -> Unit) {
    AlertDialog(
        onDismissRequest = closeModal,
        title = {
            Text(text = "Ошибка")
        },
        text = {
            Text(errorText)
        },
        confirmButton = {},
        dismissButton = {
            Button(onClick = closeModal) {
                Text("Okay")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FilmDiscoverTheme {
        HomeScreen()
    }
}