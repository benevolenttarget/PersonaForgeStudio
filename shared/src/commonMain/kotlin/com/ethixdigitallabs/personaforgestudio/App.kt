package com.ethixdigitallabs.personaforgestudio

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ethixdigitallabs.personaforgestudio.engine.BootManager
import com.ethixdigitallabs.personaforgestudio.navigation.PersonaForgeNavigation
import com.ethixdigitallabs.personaforgestudio.ui.cinematic.CinematicSequence
import com.ethixdigitallabs.personaforgestudio.ui.intro.IntroScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        val introComplete = BootManager.introCompleted

        if (introComplete) {
            PersonaForgeNavigation()
        } else {
            CinematicSequence(
                onFinished = {
                    BootManager.finishIntro()
                }
            )
        }
    }
}