package com.ethixdigitallabs.personaforgestudio.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val HologramBlue = Color(0xFF42A5F5)
private val SystemRed = Color(0xFFFF3B30)
private val AIGreen = Color(0xFF7CFF6B)

@Composable
fun HomeScreen(
    onCreateCharacter: () -> Unit = {},
    onCreateAdventure: () -> Unit = {},
    onOpenProject: () -> Unit = {},
    onSettings: () -> Unit = {}
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(28.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "PERSONAFORGE",
                color = HologramBlue,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "CYBORG OS ONLINE",
                color = SystemRed,
                fontSize = 16.sp
            )

            Spacer(Modifier.height(50.dp))

            HologramCommand(
                "◎ CREATE CHARACTER",
                onCreateCharacter
            )

            Spacer(Modifier.height(18.dp))

            HologramCommand(
                "◎ CREATE WORLD",
                onCreateAdventure
            )

            Spacer(Modifier.height(18.dp))

            HologramCommand(
                "◎ PROJECT LIBRARY",
                onOpenProject
            )

            Spacer(Modifier.height(18.dp))

            HologramCommand(
                "◎ SYSTEM SETTINGS",
                onSettings
            )

            Spacer(Modifier.height(60.dp))

            Text(
                text = "AI CORE ONLINE",
                color = AIGreen,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = "CYNTHIA",
                color = HologramBlue,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "\"Awaiting your command...\"",
                color = Color.White
            )
        }
    }
}

@Composable
private fun HologramCommand(
    text: String,
    onClick: () -> Unit
) {
    Text(
        text = text,
        color = HologramBlue,
        fontSize = 22.sp,
        modifier = Modifier.clickable {
            onClick()
        }
    )
}