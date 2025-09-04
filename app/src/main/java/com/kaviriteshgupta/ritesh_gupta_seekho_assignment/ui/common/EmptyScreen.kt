package com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.theme.Cyan

@Composable
fun EmptyScreen(errMessage: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = errMessage,
            color = Cyan,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}