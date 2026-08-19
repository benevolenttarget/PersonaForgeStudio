package com.ethixdigitallabs.personaforgestudio.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import kotlinx.coroutines.delay

@Composable
fun TypewriterText(
    text: String,
    delayMillis: Long = 50
) {
    var displayedText by remember { mutableStateOf("") }

    LaunchedEffect(text) {
        displayedText = ""
        text.forEach { char ->
            displayedText += char
            delay(delayMillis)
        }
    }

    Text(
        text = displayedText,
        style = MaterialTheme.typography.bodyLarge
    )
}
