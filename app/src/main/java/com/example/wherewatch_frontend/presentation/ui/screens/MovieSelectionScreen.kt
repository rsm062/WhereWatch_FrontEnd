package com.example.wherewatch_frontend.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.wherewatch_frontend.R
import com.example.wherewatch_frontend.domain.model.Movie
import com.example.wherewatch_frontend.presentation.navigation.Screens
import com.example.wherewatch_frontend.presentation.viewmodel.MovieDetailsViewModel
import java.time.LocalDate

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
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background_movie_detail),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                when {
                    isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
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
                                    navController.navigate(
                                        Screens.MovieDetailsScreen.createRoute(
                                            movie.id
                                        )
                                    )
                                }
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
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f) // Ocupa el espacio restante
            ) {
                Text(movie.title, style = MaterialTheme.typography.titleMedium)
                movie.releaseDate?.let {
                    val year = try {
                        LocalDate.parse(it).year
                    } catch (e: Exception) {
                        "Unknown"
                    }
                    Text(
                        text = "Release: $year",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Text(
                    text = "Rating: ${movie.rating ?: 0.0}"
                )
                Text(
                    text = "id: ${movie.id}"
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Imagen poster a la derecha
            movie.posterPath?.let { path ->
                val imageUrl = "https://image.tmdb.org/t/p/w500$path"
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Poster de ${movie.title}",
                    modifier = Modifier
                        .size(width = 100.dp, height = 150.dp)
                )
            }
        }
    }
}
