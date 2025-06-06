package com.example.wherewatch_frontend.presentation.ui.screens

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
import androidx.compose.material3.MaterialTheme
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
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    searchViewModel: SearchViewModel
) {
    val query by searchViewModel.searchData.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        // 🔽 Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.background_movie_detail),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        // 🔼 Fondo semitransparente para mejorar legibilidad (opcional)
        Column(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Black.copy(alpha = 0.3f)) // Puedes ajustar opacidad
        ) {}

        // 🔼 Contenido principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Where Watch",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 2.sp
                ),
                modifier = Modifier.padding(bottom = 40.dp),
                color = Color.White
            )

            OutlinedTextField(
                value = query,
                onValueChange = { searchViewModel.setSearchData(it) },
                label = { Text("Título de la película", color = Color.Gray) },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.DarkGray,
                    cursorColor = Color.Black,
                    containerColor = Color.White // Fondo blanco
                ),
                modifier = Modifier.fillMaxWidth()
            )


            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val safeQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString())
                    navController.navigate(Screens.MovieSelectionScreen.createRoute(safeQuery))
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
