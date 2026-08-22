package com.ethixdigitallabs.personaforgestudio.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.ethixdigitallabs.personaforgestudio.ui.screens.CharacterWizard
import com.ethixdigitallabs.personaforgestudio.ui.screens.ExperienceSection
import com.ethixdigitallabs.personaforgestudio.ui.screens.HologramExperienceScreen
import com.ethixdigitallabs.personaforgestudio.ui.screens.HomeScreen

@Composable
fun PersonaForgeNavigation() {
    var currentRoute by remember { mutableStateOf(Routes.HOME) }

    when (currentRoute) {
        Routes.HOME -> HomeScreen(
            onCreateCharacter = { currentRoute = Routes.CHARACTER_WIZARD },
            onCreateAdventure = { currentRoute = Routes.WORLD_FORGE },
            onOpenProject = { currentRoute = Routes.PROJECTS },
            onSettings = { currentRoute = Routes.DEVICE_SYSTEM }
        )

        Routes.CHARACTER_WIZARD -> CharacterWizard(
            onBack = { currentRoute = Routes.HOME }
        )

        Routes.WORLD_FORGE -> HologramExperienceScreen(
            ExperienceSection.WORLD,
            onBack = { currentRoute = Routes.HOME }
        )

        Routes.STORY_MODE -> HologramExperienceScreen(
            ExperienceSection.STORY,
            onBack = { currentRoute = Routes.HOME }
        )

        Routes.PROJECTS -> HologramExperienceScreen(
            ExperienceSection.PROJECTS,
            onBack = { currentRoute = Routes.HOME }
        )

        Routes.INVENTORY -> HologramExperienceScreen(
            ExperienceSection.INVENTORY,
            onBack = { currentRoute = Routes.HOME }
        )

        Routes.STORE -> HologramExperienceScreen(
            ExperienceSection.STORE,
            onBack = { currentRoute = Routes.HOME }
        )

        Routes.DEVICE_SYSTEM -> HologramExperienceScreen(
            ExperienceSection.DEVICE,
            onBack = { currentRoute = Routes.HOME }
        )
    }
}
