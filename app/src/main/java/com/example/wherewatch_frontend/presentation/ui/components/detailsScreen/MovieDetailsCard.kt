package com.example.wherewatch_frontend.presentation.ui.components.detailsScreen

import com.example.wherewatch_frontend.domain.utils.ImageUrlHelper
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.wherewatch_frontend.domain.model.Movie

/**
 * Displays an expandable card showing movie details such as overview and release date,
 * with the movie poster image aligned to the right.
 *
 * @param movie The [Movie] object containing data to display.
 * @param modifier Optional [Modifier] to apply to the card.
 */
@Composable
fun MovieDetailsCard(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        onClick = { expanded = !expanded },
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Detalles", style = MaterialTheme.typography.titleMedium)
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        movie.overview?.let {
                            Text(
                                text = it,
                                maxLines = 6,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        movie.releaseDate?.let {
                            Text("Estreno: $it", style = MaterialTheme.typography.bodySmall)
                        }
                    }

                    movie.posterPath?.let { path ->
                        ImageUrlHelper.buildPosterUrl(path)?.let { imageUrl ->
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
        }
    }
}
