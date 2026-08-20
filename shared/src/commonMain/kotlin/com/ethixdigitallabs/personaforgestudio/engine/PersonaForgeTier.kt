package com.ethixdigitallabs.personaforgestudio.engine

enum class PersonaForgeTier {
    FREEMIUM,
    PREMIUM,
    VIP
}

data class PersonaForgeCapabilities(
    val worldCreationScope: String,
    val maxRenderResolution: String,
    val modelDownloadLimit: Int?,
    val modelCreationLimit: Int?,
    val forgeSpeed: String,
    val fullPlanetCreation: Boolean
)

fun PersonaForgeTier.capabilities(): PersonaForgeCapabilities = when (this) {
    PersonaForgeTier.FREEMIUM -> PersonaForgeCapabilities(
        worldCreationScope = "UP TO 2 CONTINENTS",
        maxRenderResolution = "BASIC PBR / SHADED PBR",
        modelDownloadLimit = 1,
        modelCreationLimit = null,
        forgeSpeed = "STANDARD",
        fullPlanetCreation = false
    )
    PersonaForgeTier.PREMIUM -> PersonaForgeCapabilities(
        worldCreationScope = "EXPANDED WORLD CREATION",
        maxRenderResolution = "UP TO 4K",
        modelDownloadLimit = 10,
        modelCreationLimit = null,
        forgeSpeed = "FAST",
        fullPlanetCreation = false
    )
    PersonaForgeTier.VIP -> PersonaForgeCapabilities(
        worldCreationScope = "COMPLETE CUSTOM PLANET",
        maxRenderResolution = "UP TO 8K",
        modelDownloadLimit = null,
        modelCreationLimit = null,
        forgeSpeed = "ULTRA",
        fullPlanetCreation = true
    )
}
