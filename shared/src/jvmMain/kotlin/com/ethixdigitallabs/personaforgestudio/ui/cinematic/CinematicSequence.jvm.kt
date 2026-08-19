package com.ethixdigitallabs.personaforgestudio.ui.cinematic

import androidx.compose.runtime.Composable

@Composable
actual fun CinematicSequence(
    onFinished: () -> Unit
) {

    CinematicPlayer(

        onFinished = {

            onFinished()

        }

    )

}