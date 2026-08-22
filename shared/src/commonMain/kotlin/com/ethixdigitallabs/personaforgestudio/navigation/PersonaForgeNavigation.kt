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
            onSettings = { currentRoute = Routes.DEVICE_SYSTEM },
            onStoryMode = { currentRoute = Routes.STORY_MODE },
            onInventory = { currentRoute = Routes.INVENTORY },
            onStore = { currentRoute = Routes.STORE }
        )

        Routes.CHARACTER_WIZARD -> CharacterWizard { currentRoute = Routes.HOME }
        Routes.WORLD_FORGE -> HologramExperienceScreen(ExperienceSection.WORLD) { currentRoute = Routes.HOME }
        Routes.STORY_MODE -> HologramExperienceScreen(ExperienceSection.STORY) { currentRoute = Routes.HOME }
        Routes.PROJECTS -> HologramExperienceScreen(ExperienceSection.PROJECTS) { currentRoute = Routes.HOME }
        Routes.INVENTORY -> HologramExperienceScreen(ExperienceSection.INVENTORY) { currentRoute = Routes.HOME }
        Routes.STORE -> HologramExperienceScreen(ExperienceSection.STORE) { currentRoute = Routes.HOME }
        Routes.DEVICE_SYSTEM -> HologramExperienceScreen(ExperienceSection.DEVICE) { currentRoute = Routes.HOME }
    }
}
