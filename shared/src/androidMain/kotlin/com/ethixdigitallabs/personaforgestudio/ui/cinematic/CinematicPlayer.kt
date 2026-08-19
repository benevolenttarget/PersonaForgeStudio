package com.ethixdigitallabs.personaforgestudio.ui.cinematic

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView

@Composable
fun CinematicPlayer(
    videoRes: Int,
    modifier: Modifier = Modifier,
    onFinished: () -> Unit = {}
) {

    val context = LocalContext.current

    // Create ONE player
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build()
    }

    // Load a NEW movie whenever videoRes changes
    LaunchedEffect(videoRes) {

        val uri = Uri.parse(
            "android.resource://${context.packageName}/$videoRes"
        )

        exoPlayer.setMediaItem(
            MediaItem.fromUri(uri)
        )

        exoPlayer.prepare()

        exoPlayer.playWhenReady = true
    }

    DisposableEffect(exoPlayer) {

        val listener = object : Player.Listener {

            override fun onPlaybackStateChanged(state: Int) {

                if (state == Player.STATE_ENDED) {

                    onFinished()

                }

            }

        }

        exoPlayer.addListener(listener)

        onDispose {

            exoPlayer.removeListener(listener)

            exoPlayer.release()

        }

    }

    AndroidView(

        modifier = modifier.fillMaxSize(),

        factory = {

            PlayerView(it).apply {

                player = exoPlayer

                useController = false

                resizeMode =
                    AspectRatioFrameLayout.RESIZE_MODE_ZOOM

                setShowBuffering(
                    PlayerView.SHOW_BUFFERING_NEVER
                )

                keepScreenOn = true

            }

        }

    )

}