package com.ethixdigitallabs.personaforgestudio.ui.intro

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun IntroScreen(
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "3040"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "A damaged cyborg crashes to Earth..."
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onContinue
        ) {
            Text("Continue")
        }

    }
}