package com.example.wherewatch_frontend.presentation.ui.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wherewatch_frontend.R
import com.example.wherewatch_frontend.presentation.navigation.Screens
import com.example.wherewatch_frontend.presentation.viewmodel.SearchViewModel
import com.example.wherewatch_frontend.ui.theme.WhereWatch_FrontEndTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults


/**
 * Screen that allows the user to input a movie title query to search for movies.
 * Displays a background image, a logo, a text input field, and a search button.
 * When the search button is pressed, navigates to the MovieSelectionScreen with the encoded query.
 *
 * @param navController [NavController] used for navigation between screens.
 * @param searchViewModel [SearchViewModel] used to manage the search query state.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    searchViewModel: SearchViewModel
) {
    val query by searchViewModel.searchData.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.dostirasdepeliculaperforada),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Image(
                painter = painterResource(id = R.drawable.where_watch_logo),
                contentDescription = "Logo Where Watch",
                modifier = Modifier
                    .size(350.dp)
                    .padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = query,
                onValueChange = { searchViewModel.setSearchData(it) },
                label = { Text("Título de la película", color = Color.Black) },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.DarkGray,
                    cursorColor = Color.Black,
                    containerColor = Color.White// White background for better visivility
                ),
                modifier = Modifier.fillMaxWidth()
            )


            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // Encode the query safely to be passed in the route
                    val safeQuery = Uri.encode(query)
                    navController.navigate(Screens.MovieSelectionScreen.createRoute(safeQuery))
                    searchViewModel.clearSearchData()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Buscar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    WhereWatch_FrontEndTheme {
        SearchScreen(rememberNavController(), viewModel())
    }
}
