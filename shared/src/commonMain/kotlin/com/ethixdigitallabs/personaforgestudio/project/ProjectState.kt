package com.ethixdigitallabs.personaforgestudio.project

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object ProjectState {
    var currentCharacter by mutableStateOf(Character())
}

class Character(name: String = "") {
    var name by mutableStateOf(name)
}
