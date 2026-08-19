package com.ethixdigitallabs.personaforgestudio

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform