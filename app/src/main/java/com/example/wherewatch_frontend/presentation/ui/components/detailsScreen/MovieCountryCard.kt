package com.example.wherewatch_frontend.presentation.ui.components.detailsScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.wherewatch_frontend.domain.model.Country
import com.example.wherewatch_frontend.domain.model.Platform
import com.example.wherewatch_frontend.domain.utils.ImageUrlHelper

/**
 * Card component that displays a country's name and its available platforms.
 *
 * Each platform is represented with its logo.
 *
 * @param country Country for which platform availability is shown.
 * @param platforms List of platforms available in the given country.
 */
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                platforms.forEach { platform ->
                    platform.logoPath?.let { logoPath ->
                        val logoFullUrl = ImageUrlHelper.buildLogoUrl(logoPath)
                        AsyncImage(
                            model = logoFullUrl,
                            contentDescription = platform.name,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                    }
                }
            }
        }
    }
}