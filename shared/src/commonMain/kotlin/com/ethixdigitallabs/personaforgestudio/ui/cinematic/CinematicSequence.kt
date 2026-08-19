package com.ethixdigitallabs.personaforgestudio.ui.cinematic

import androidx.compose.runtime.Composable

@Composable
expect fun CinematicSequence(
    onFinished: () -> Unit
)
