package com.example.wherewatch_frontend.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wherewatch_frontend.R
import com.example.wherewatch_frontend.presentation.navigation.Screens
import com.example.wherewatch_frontend.presentation.ui.components.selectionScreen.EmptyResultsMessage
import com.example.wherewatch_frontend.presentation.ui.components.selectionScreen.ErrorMessage
import com.example.wherewatch_frontend.presentation.ui.components.selectionScreen.LoadingIndicator
import com.example.wherewatch_frontend.presentation.ui.components.selectionScreen.MovieCardItem
import com.example.wherewatch_frontend.presentation.viewmodel.MovieSelectionViewModel


/**
 * Composable screen that displays search results for movies by title.
 *
 * @param navController Used for navigating between screens.
 * @param viewModel The ViewModel containing logic and state for movie selection.
 * @param title The movie title used to search and display results.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieSelectionScreen(
    navController: NavController,
    viewModel: MovieSelectionViewModel,
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
                painter = painterResource(id = R.drawable.dostirasdepeliculaperforada),
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
                        LoadingIndicator()
                    }

                    error != null -> {
                        ErrorMessage("Tenemos problemas con nuestros servidores en este momento," +
                                " por favor inténtelo de nuevo más tarde o póngase en contacto " +
                                "con nuestro servicio técnico y transmita el error: $error")
                    }

                    movies.isEmpty() -> {
                        EmptyResultsMessage("No se encontraron resultados.")
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


