package com.ethixdigitallabs.personaforgestudio.engine

enum class PersonaForgeTier {
    FREEMIUM,
    PREMIUM,
    VIP
}

data class PersonaForgeCapabilities(
    val worldContinents: Int?,
    val maxRenderResolution: String,
    val modelDownloadLimit: Int?,
    val modelCreationLimit: Int?,
    val forgeSpeed: String,
    val fullPlanetCreation: Boolean,
    val advancedLandscapes: Boolean
)

fun PersonaForgeTier.capabilities(): PersonaForgeCapabilities = when (this) {
    PersonaForgeTier.FREEMIUM -> PersonaForgeCapabilities(
        worldContinents = 2,
        maxRenderResolution = "PBR",
        modelDownloadLimit = 1,
        modelCreationLimit = 10,
        forgeSpeed = "STANDARD",
        fullPlanetCreation = false,
        advancedLandscapes = false
    )
    PersonaForgeTier.PREMIUM -> PersonaForgeCapabilities(
        worldContinents = null,
        maxRenderResolution = "4K",
        modelDownloadLimit = 10,
        modelCreationLimit = 50,
        forgeSpeed = "FAST",
        fullPlanetCreation = false,
        advancedLandscapes = true
    )
    PersonaForgeTier.VIP -> PersonaForgeCapabilities(
        worldContinents = null,
        maxRenderResolution = "8K",
        modelDownloadLimit = null,
        modelCreationLimit = null,
        forgeSpeed = "ULTRA",
        fullPlanetCreation = true,
        advancedLandscapes = true
    )
}
