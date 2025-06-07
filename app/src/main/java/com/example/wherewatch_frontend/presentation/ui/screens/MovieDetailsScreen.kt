package com.example.wherewatch_frontend.presentation.ui.screens

import com.example.wherewatch_frontend.presentation.viewmodel.MovieDetailsViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wherewatch_frontend.presentation.navigation.Screens
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.wherewatch_frontend.R
import com.example.wherewatch_frontend.presentation.ui.components.detailsScreen.FiltersSection
import com.example.wherewatch_frontend.presentation.ui.components.detailsScreen.LoadingContent
import com.example.wherewatch_frontend.presentation.ui.components.detailsScreen.MovieCountryCard
import com.example.wherewatch_frontend.presentation.ui.components.detailsScreen.MovieDetailsCard

/**
 * Composable screen that displays the details of a selected movie.
 *
 * Includes movie information, filter controls for country and platform, and a list
 * of availability grouped by country.
 *
 * @param navController Navigation controller used to handle screen transitions.
 * @param viewModel ViewModel providing the movie data and filter logic.
 * @param id The ID of the movie to be displayed.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    navController: NavController,
    viewModel: MovieDetailsViewModel,
    id: Int
) {
    val movie by viewModel.movie.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.errorMessage.collectAsState()
    val filteredAvailabilities by viewModel.filteredAvailabilities.collectAsState()

    var filtersExpanded by remember { mutableStateOf(false) }
    LaunchedEffect(id) {
        viewModel.loadMovieById(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { movie?.let { Text(text = it.title) } },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.Search, contentDescription = "Go back to list")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screens.SearchScreen.route) {
                            popUpTo(Screens.SearchScreen.route) { inclusive = true }
                        }
                    }) {
                        Icon(Icons.Default.Home, contentDescription = "Inicio")
                    }
                },

                )
        }, floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.clearFilters()
            }) {
                Icon(Icons.Default.Clear, contentDescription = "Limpiar filtros")
            }
        },
        content = { innerPadding ->
            Box(modifier = Modifier.fillMaxSize()) {
                Image(//Background picture
                    painter = painterResource(id = R.drawable.dostirasdepeliculaperforada),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )
                LoadingContent(isLoading = isLoading, error = error) {
                    movie?.let { movie ->
                        Column(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            // Expansive Card for detalles
                            MovieDetailsCard(movie = movie)

                            Spacer(modifier = Modifier.height(16.dp))

                            FiltersSection(
                                platforms = movie.availabilities.map { it.platform },
                                selectedPlatforms = viewModel.selectedPlatforms.collectAsState().value,
                                onPlatformToggle = { viewModel.togglePlatform(it) },
                                countries = movie.availabilities.map { it.country },
                                selectedCountries = viewModel.selectedCountries.collectAsState().value,
                                onCountryToggle = { viewModel.toggleCountry(it) },
                                expanded = filtersExpanded,
                                onExpandToggle = { filtersExpanded = !filtersExpanded }
                            )

                            Spacer(modifier = Modifier.height(16.dp))


                            LazyColumn(modifier = Modifier.weight(1f)) {
                                val grouped = filteredAvailabilities.groupBy { it.country }
                                grouped.forEach { (country, platforms) ->
                                    item {
                                        MovieCountryCard(
                                            country = country,
                                            platforms = platforms.map { it.platform })
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}




