package com.example.wherewatch_frontend.presentation.ui.screens

import com.example.wherewatch_frontend.presentation.viewmodel.MovieDetailsViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.wherewatch_frontend.domain.model.Country
import com.example.wherewatch_frontend.domain.model.Platform
import com.example.wherewatch_frontend.presentation.navigation.Screens
import com.example.wherewatch_frontend.ui.theme.WhereWatch_FrontEndTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    navController: NavController,
    viewModel: MovieDetailsViewModel
) {
    val movie by viewModel.movie.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.errorMessage.collectAsState()
    val filteredAvailabilities by viewModel.filteredAvailabilities.collectAsState()

    var detailsExpanded by remember { mutableStateOf(false) }
    var filtersExpanded by remember { mutableStateOf(false) }

    /*LaunchedEffect(title) {
        viewModel.loadMovieByTitle(title)
    }*/

    Scaffold(
        topBar = {
            TopAppBar(
                title = { movie?.let { Text(text = it.title) } },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack() }) {
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
                }
            )
        },floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.clearFilters()
            }) {
                Icon(Icons.Default.Clear, contentDescription = "Limpiar filtros")
            }
        },
        content = { innerPadding ->
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (error != null) {
                Text("Error: $error", color = Color.Red, modifier = Modifier.padding(16.dp))
            } else {
                movie?.let { movie ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        // Card de detalles expandible
                        Card(
                            onClick = { detailsExpanded = !detailsExpanded },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Detalles", style = MaterialTheme.typography.titleMedium)
                                AnimatedVisibility(
                                    visible = detailsExpanded,
                                    enter = expandVertically(),
                                    exit = shrinkVertically()
                                ) {
                                    Column(modifier = Modifier.padding(top = 8.dp)) {
                                        movie.overview?.let {
                                            Text(it)
                                            Spacer(modifier = Modifier.height(8.dp))
                                        }
                                        movie.releaseDate?.let {
                                            Text("Estreno: $it")
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Filtros
                        Button(onClick = { filtersExpanded = !filtersExpanded }) {
                            Text("Filtrar")
                        }

                        AnimatedVisibility(
                            visible = filtersExpanded,
                            enter = expandVertically(),
                            exit = shrinkVertically()
                        ) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 8.dp)
                                ) {
                                    Text("Plataformas", fontWeight = FontWeight.Bold)
                                    movie.availabilities.map { it.platform }.distinct()
                                        .forEach { platform ->
                                            val selected =
                                                viewModel.selectedPlatforms.collectAsState().value.contains(
                                                    platform
                                                )
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .toggleable(
                                                        value = selected,
                                                        onValueChange = {
                                                            viewModel.togglePlatform(
                                                                platform
                                                            )
                                                        })
                                            ) {
                                                Checkbox(checked = selected, onCheckedChange = null)
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(platform.name)
                                            }
                                        }
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Países", fontWeight = FontWeight.Bold)
                                    movie.availabilities.map { it.country }.distinct()
                                        .forEach { country ->
                                            val selected =
                                                viewModel.selectedCountries.collectAsState().value.contains(
                                                    country
                                                )
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .toggleable(
                                                        value = selected,
                                                        onValueChange = {
                                                            viewModel.toggleCountry(
                                                                country
                                                            )
                                                        })
                                            ) {
                                                Checkbox(checked = selected, onCheckedChange = null)
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(country.name)
                                            }
                                        }
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            // Botón de aplicar filtro manual (comentado)
                            // Button(onClick = { filtersExpanded = false }) { Text("Aplicar Filtros") }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Listado filtrado
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
    )
}

@Composable
fun MovieCountryCard(
    country: Country,
    platforms: List<Platform>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = country.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            platforms.forEach { platform ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth()
                ) {
                    platform.logoUrl?.let { url ->
                        AsyncImage(
                            model = url,
                            contentDescription = platform.name,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                    }

                    Text(
                        text = platform.name,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieCountryCardPreview() {
    val sampleCountry = Country(
        id = 1,
        name = "España",
        isoCode = "ES"
    )

    val samplePlatforms = listOf(
        Platform(id = 1, name = "Netflix", logoUrl = null),
        Platform(id = 2, name = "HBO Max", logoUrl = null),
        Platform(id = 3, name = "Amazon Prime", logoUrl = null)
    )

    WhereWatch_FrontEndTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            MovieCountryCard(
                country = sampleCountry,
                platforms = samplePlatforms
            )
        }
    }
}



