package com.example.wherewatch_frontend.presentation.ui.components.detailsScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wherewatch_frontend.domain.model.Country
import com.example.wherewatch_frontend.domain.model.Platform
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width


/**
 * Expandable section that displays filters for platforms and countries.
 *
 * Allows the user to toggle which platforms and countries are used
 * to filter the movie availabilities.
 *
 * @param platforms List of available platforms to display.
 * @param selectedPlatforms List of currently selected platforms.
 * @param onPlatformToggle Callback invoked when a platform is toggled.
 * @param countries List of available countries to display.
 * @param selectedCountries List of currently selected countries.
 * @param onCountryToggle Callback invoked when a country is toggled.
 * @param expanded Whether the filter section is expanded.
 * @param onExpandToggle Callback to expand/collapse the section.
 */
@Composable
fun FiltersSection(
    platforms: List<Platform>,
    selectedPlatforms: Set<Platform>,
    onPlatformToggle: (Platform) -> Unit,
    countries: List<Country>,
    selectedCountries: Set<Country>,
    onCountryToggle: (Country) -> Unit,
    expanded: Boolean,
    onExpandToggle: () -> Unit,
) {
    Column {
        Button(onClick = onExpandToggle) {
            Text("Filtros")
        }

        AnimatedVisibility(
            visible = expanded,
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
                    platforms.distinct().forEach { platform ->
                        val selected = selectedPlatforms.contains(platform)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .toggleable(
                                    value = selected,
                                    onValueChange = { onPlatformToggle(platform) }
                                )
                        ) {
                            Checkbox(
                                checked = selected,
                                onCheckedChange = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(platform.name)
                        }
                    }
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("Países", fontWeight = FontWeight.Bold)
                    countries.distinct().forEach { country ->
                        val selected = selectedCountries.contains(country)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .toggleable(
                                    value = selected,
                                    onValueChange = { onCountryToggle(country) }
                                )
                        ) {
                            Checkbox(
                                checked = selected,
                                onCheckedChange = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(country.name)
                        }
                    }
                }
            }
        }
    }
}