package com.example.wherewatch_frontend.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wherewatch_frontend.domain.model.Movie
import com.example.wherewatch_frontend.presentation.navigation.Screens
import com.example.wherewatch_frontend.presentation.viewmodel.MovieDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieSelectionScreen(
    navController: NavController,
    viewModel: MovieDetailsViewModel,
    title: String
) {
    val movies by viewModel.searchResults.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.errorMessage.collectAsState()

    LaunchedEffect(title) {
        viewModel.searchMoviesByTitle(title)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Selecciona una película") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screens.SearchScreen.route) {
                            popUpTo(Screens.SearchScreen.route) { inclusive = true }
                        }
                    }) {
                        Icon(Icons.Default.Home, contentDescription = "Ir a inicio")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .padding(16.dp)) {

            when {
                isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                error != null -> {
                    Text("Error: $error", color = Color.Red)
                }

                movies.isEmpty() -> {
                    Text("No se encontraron resultados.")
                }

                else -> {
                    LazyColumn {
                        items(movies) { movie ->
                            MovieCardItem(movie = movie) {
                                viewModel.selectMovie(movie)
                                navController.navigate(Screens.MovieDetailsScreen.route)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieCardItem(
    movie: Movie,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(movie.title, style = MaterialTheme.typography.titleMedium)
            Text(
                text = movie.releaseDate ?: "Fecha desconocida",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = movie.overview ?: "",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
