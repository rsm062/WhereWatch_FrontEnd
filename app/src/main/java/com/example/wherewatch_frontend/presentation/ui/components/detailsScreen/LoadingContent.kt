package com.example.wherewatch_frontend.presentation.ui.components.detailsScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding

/**
 * Displays a loading indicator, error message, or main content based on state.
 *
 * @param isLoading Whether data is currently being loaded.
 * @param error Error message to display, if any.
 * @param content Composable block representing the main content to show.
 */
@Composable
fun LoadingContent(
    isLoading: Boolean,
    error: String?,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            isLoading -> CircularProgressIndicator()
            error != null -> Text("Error: $error", color = Color.Red, modifier = Modifier.padding(16.dp))
            else -> content()
        }
    }
}
