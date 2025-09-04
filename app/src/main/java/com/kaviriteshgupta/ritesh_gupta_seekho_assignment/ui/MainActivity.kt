package com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.navigation.AppNavigation
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.theme.RiteshguptaseekhoassignmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RiteshguptaseekhoassignmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(paddingValues = innerPadding)
                }
            }
        }
    }
}