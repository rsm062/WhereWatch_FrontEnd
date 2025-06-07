package com.example.wherewatch_frontend.presentation.ui.components.selectionScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.wherewatch_frontend.domain.model.Movie
import com.example.wherewatch_frontend.domain.utils.ImageUrlHelper
import java.time.LocalDate


/**
 * Displays a card representing a movie, showing title, release year, rating, and poster.
 *
 * @param movie The movie to display.
 * @param onClick Action to perform when the card is clicked (e.g., navigate to detail).
 */
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
                modifier = Modifier.weight(1f)
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

            // Image poster film
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