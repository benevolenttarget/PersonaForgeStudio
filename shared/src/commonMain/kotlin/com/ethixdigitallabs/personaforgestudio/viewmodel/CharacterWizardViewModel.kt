package com.ethixdigitallabs.personaforgestudio.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class CharacterWizardViewModel {

    var step by mutableStateOf(0)
        private set

    fun nextStep() {
        step++
    }

    fun previousStep() {

        if (step > 0)
            step--

    }

}