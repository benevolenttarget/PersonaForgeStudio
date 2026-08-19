package com.ethixdigitallabs.personaforgestudio.engine

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object BootManager {

    var introCompleted by mutableStateOf(false)

    fun finishIntro() {
        introCompleted = true
    }

    fun resetIntro() {
        introCompleted = false
    }
}