package com.ethixdigitallabs.personaforgestudio.ui.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.sin
import kotlin.random.Random

private val HologramBlue = Color(0xFF42A5F5)
private val SystemRed = Color(0xFFFF3030)
private val AIGreen = Color(0xFF7CFF6B)
private val HoloGreen = Color(0xFF39FF88)
private val GlassBlack = Color(0xCC020607)

private data class Particle(
    val x: Float,
    val y: Float,
    val radius: Float,
    val phase: Float,
    val speed: Float
)

@Composable
fun HomeScreen(
    onCreateCharacter: () -> Unit = {},
    onCreateAdventure: () -> Unit = {},
    onOpenProject: () -> Unit = {},
    onSettings: () -> Unit = {}
) {
    val transition = rememberInfiniteTransition(label = "hologram")
    val pulse = transition.animateFloat(
        initialValue = 0.35f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    val scan = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4200),
            repeatMode = RepeatMode.Restart
        ),
        label = "scan"
    )

    val particles = remember {
        List(70) {
            Particle(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                radius = 0.8f + Random.nextFloat() * 2.2f,
                phase = Random.nextFloat() * 6.28f,
                speed = 0.4f + Random.nextFloat() * 1.4f
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        CinematicDesertBackground(
            particles = particles,
            pulse = pulse.value,
            scan = scan.value
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            HologramHeader(pulse = pulse.value)

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "PERSONAFORGE",
                    color = Color.White,
                    fontSize = 34.sp,
                    letterSpacing = 5.sp
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "FORGE INTERFACE // READY",
                    color = HoloGreen.copy(alpha = 0.85f),
                    fontSize = 12.sp,
                    letterSpacing = 2.sp
                )

                Spacer(Modifier.height(28.dp))

                HologramCommand("CREATE CHARACTER", onCreateCharacter)
                HologramCommand("CREATE WORLD", onCreateAdventure)
                HologramCommand("PROJECT LIBRARY", onOpenProject)
                HologramCommand("SYSTEM SETTINGS", onSettings)
            }

            HologramFooter(pulse = pulse.value)
        }
    }
}

@Composable
private fun CinematicDesertBackground(
    particles: List<Particle>,
    pulse: Float,
    scan: Float
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF071116),
                        Color(0xFF17251F),
                        Color(0xFF5A432D),
                        Color(0xFF120D0A)
                    )
                )
            )
    ) {
        val w = size.width
        val h = size.height

        // Distant atmospheric mountains.
        val mountain = PathBuilder()
        mountain.moveTo(0f, h * 0.55f)
        mountain.lineTo(w * 0.14f, h * 0.39f)
        mountain.lineTo(w * 0.25f, h * 0.51f)
        mountain.lineTo(w * 0.39f, h * 0.31f)
        mountain.lineTo(w * 0.53f, h * 0.50f)
        mountain.lineTo(w * 0.69f, h * 0.34f)
        mountain.lineTo(w * 0.82f, h * 0.48f)
        mountain.lineTo(w, h * 0.37f)
        mountain.lineTo(w, h)
        mountain.lineTo(0f, h)
        mountain.close()
        drawPath(
            path = mountain.path,
            color = Color(0xFF111B1A).copy(alpha = 0.88f)
        )

        // Warm desert floor.
        drawRect(
            brush = Brush.verticalGradient(
                listOf(
                    Color(0xFF8C6742).copy(alpha = 0.25f),
                    Color(0xFF24170E).copy(alpha = 0.9f)
                ),
                startY = h * 0.54f,
                endY = h
            ),
            topLeft = Offset(0f, h * 0.54f),
            size = androidx.compose.ui.geometry.Size(w, h * 0.46f)
        )

        // Holographic horizon grid.
        val horizon = h * 0.67f
        for (i in 0..9) {
            val t = i / 10f
            val y = horizon + (h - horizon) * t * t
            drawLine(
                color = HoloGreen.copy(alpha = 0.07f + pulse * 0.03f),
                start = Offset(0f, y),
                end = Offset(w, y),
                strokeWidth = 1f
            )
        }

        for (i in -10..10) {
            val x = w / 2f + i * w * 0.065f
            drawLine(
                color = HoloGreen.copy(alpha = 0.06f),
                start = Offset(w / 2f, horizon),
                end = Offset(x, h),
                strokeWidth = 1f
            )
        }

        // Lime-green floating particles.
        particles.forEach { particle ->
            val drift = sin(scan * particle.speed * 6.28f + particle.phase) * 8f
            val x = particle.x * w + drift
            val y = (particle.y * h - scan * 32f * particle.speed + h) % h
            drawCircle(
                color = HoloGreen.copy(alpha = 0.18f + pulse * 0.32f),
                radius = particle.radius,
                center = Offset(x, y)
            )
        }

        // Moving scan line.
        val scanY = scan * h
        drawLine(
            color = HoloGreen.copy(alpha = 0.055f),
            start = Offset(0f, scanY),
            end = Offset(w, scanY),
            strokeWidth = 2f
        )

        // Central holographic glow.
        drawCircle(
            color = HoloGreen.copy(alpha = 0.035f + pulse * 0.025f),
            radius = w * 0.36f,
            center = Offset(w / 2f, h * 0.55f)
        )
    }
}

@Composable
private fun HologramHeader(pulse: Float) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "CYNTHIA // AI CORE",
            color = HoloGreen.copy(alpha = 0.75f + pulse * 0.2f),
            fontSize = 11.sp,
            letterSpacing = 1.5.sp
        )

        Text(
            text = "SYSTEM ONLINE",
            color = SystemRed.copy(alpha = 0.75f + pulse * 0.2f),
            fontSize = 11.sp,
            letterSpacing = 1.5.sp
        )
    }
}

@Composable
private fun HologramCommand(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .background(GlassBlack)
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 15.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "◆",
                color = HoloGreen,
                fontSize = 13.sp
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = text,
                color = Color.White,
                fontSize = 16.sp,
                letterSpacing = 2.sp
            )
        }
    }
}

@Composable
private fun HologramFooter(pulse: Float) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "HOLOGRAPHIC FORGE ENVIRONMENT",
            color = HologramBlue.copy(alpha = 0.65f),
            fontSize = 10.sp,
            letterSpacing = 1.2.sp
        )
        Spacer(Modifier.height(5.dp))
        Text(
            text = "CYNTHIA: \"Awaiting your command...\"",
            color = Color.White.copy(alpha = 0.72f + pulse * 0.2f),
            fontSize = 12.sp
        )
    }
}

private class PathBuilder {
    val path = androidx.compose.ui.graphics.Path()
    fun moveTo(x: Float, y: Float) = path.moveTo(x, y)
    fun lineTo(x: Float, y: Float) = path.lineTo(x, y)
    fun close() = path.close()
}
