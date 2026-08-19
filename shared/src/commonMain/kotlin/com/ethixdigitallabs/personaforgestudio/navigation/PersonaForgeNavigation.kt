package com.ethixdigitallabs.personaforgestudio.navigation

import androidx.compose.runtime.*
import com.ethixdigitallabs.personaforgestudio.ui.screens.HomeScreen
import com.ethixdigitallabs.personaforgestudio.ui.screens.CharacterWizard

@Composable
fun PersonaForgeNavigation() {
    var currentRoute by remember { mutableStateOf(Routes.HOME) }

    when (currentRoute) {
        Routes.HOME -> {
            HomeScreen(
                onCreateCharacter = {
                    currentRoute = Routes.CHARACTER_WIZARD
                }
            )
        }
        Routes.CHARACTER_WIZARD -> {
            CharacterWizard(
                onBack = {
                    currentRoute = Routes.HOME
                }
            )
        }
    }
}