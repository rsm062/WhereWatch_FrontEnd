package com.example.wherewatch_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.wherewatch_frontend.presentation.navigation.NavGraph
import com.example.wherewatch_frontend.ui.theme.WhereWatch_FrontEndTheme

/**
 * MainActivity serves as the entry point of the application.
 *
 * It sets up edge-to-edge display and composes the app's navigation graph within the
 * custom app theme.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()// Enables edge-to-edge layout for immersive UI
        setContent {
            WhereWatch_FrontEndTheme {
                NavGraph()// Composes the navigation graph of the app
            }
        }
    }
}