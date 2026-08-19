package com.ethixdigitallabs.personaforgestudio.ui.cinematic

import androidx.compose.runtime.*
import com.ethixdigitallabs.personaforgestudio.shared.R

@Composable
actual fun CinematicSequence(
    onFinished: () -> Unit
) {
    val playlist = listOf(
        R.raw.opening_scene,
        R.raw.middle_sequence,
        R.raw.closing_scene
    )

    var currentVideo by remember {
        mutableStateOf(0)
    }

    CinematicPlayer(
        videoRes = playlist[currentVideo],
        onFinished = {
            if (currentVideo < playlist.lastIndex) {
                currentVideo++
            } else {
                onFinished()
            }
        }
    )
}
