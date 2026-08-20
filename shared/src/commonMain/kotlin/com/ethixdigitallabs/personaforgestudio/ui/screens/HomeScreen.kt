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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

private val HoloGreen = Color(0xFF39FF88)
private val HoloGreenBright = Color(0xFF9CFFB9)
private val SystemRed = Color(0xFFFF3B3B)
private val HologramBlue = Color(0xFF58B9FF)
private val Panel = Color(0xCC071014)
private val PanelLine = Color(0x554BFF9A)

@Composable
fun HomeScreen(
    onCreateCharacter: () -> Unit = {},
    onCreateAdventure: () -> Unit = {},
    onOpenProject: () -> Unit = {},
    onSettings: () -> Unit = {}
) {
    val transition = rememberInfiniteTransition(label = "forge_hud")
    val pulse = transition.animateFloat(
        initialValue = 0.45f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1500), RepeatMode.Reverse),
        label = "cynthia_pulse"
    )
    val scan = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(5000)),
        label = "scan"
    )
    val rotation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(12000)),
        label = "ring_rotation"
    )

    val particles = remember {
        List(85) {
            Particle(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                radius = 0.7f + Random.nextFloat() * 2.5f,
                phase = Random.nextFloat() * 6.28f,
                speed = 0.35f + Random.nextFloat() * 1.25f
            )
        }
    }

    Box(Modifier.fillMaxSize()) {
        CinematicEnvironment(
            particles = particles,
            pulse = pulse.value,
            scan = scan.value,
            rotation = rotation.value
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            SystemHeader(pulse.value)

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CynthiaProjection(pulse.value, rotation.value)

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "PERSONAFORGE",
                    color = Color.White,
                    fontSize = 32.sp,
                    letterSpacing = 5.sp
                )
                Text(
                    text = "FORGE DEVICE // ONLINE",
                    color = HoloGreenBright.copy(alpha = 0.8f),
                    fontSize = 11.sp,
                    letterSpacing = 2.4.sp
                )

                Spacer(Modifier.height(18.dp))

                StoryStatusPanel(pulse.value)

                Spacer(Modifier.height(14.dp))

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    ForgeTile(
                        modifier = Modifier.weight(1f),
                        label = "CHARACTER",
                        detail = "REAL-TIME FORGE",
                        onClick = onCreateCharacter
                    )
                    ForgeTile(
                        modifier = Modifier.weight(1f),
                        label = "WORLD",
                        detail = "ENTER THE FORGE",
                        onClick = onCreateAdventure
                    )
                }

                Spacer(Modifier.height(10.dp))

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    ForgeTile(
                        modifier = Modifier.weight(1f),
                        label = "PROJECTS",
                        detail = "YOUR CREATIONS",
                        onClick = onOpenProject
                    )
                    ForgeTile(
                        modifier = Modifier.weight(1f),
                        label = "DEVICE",
                        detail = "SYSTEM CONFIG",
                        onClick = onSettings
                    )
                }
            }

            SystemFooter(pulse.value)
        }
    }
}

private data class Particle(
    val x: Float,
    val y: Float,
    val radius: Float,
    val phase: Float,
    val speed: Float
)

@Composable
private fun CinematicEnvironment(
    particles: List<Particle>,
    pulse: Float,
    scan: Float,
    rotation: Float
) {
    Canvas(
        Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF05090C),
                        Color(0xFF0B1719),
                        Color(0xFF3D3328),
                        Color(0xFF120C08)
                    )
                )
            )
    ) {
        val w = size.width
        val h = size.height

        val mountains = Path().apply {
            moveTo(0f, h * 0.56f)
            lineTo(w * 0.12f, h * 0.42f)
            lineTo(w * 0.23f, h * 0.51f)
            lineTo(w * 0.37f, h * 0.33f)
            lineTo(w * 0.51f, h * 0.49f)
            lineTo(w * 0.67f, h * 0.35f)
            lineTo(w * 0.82f, h * 0.50f)
            lineTo(w, h * 0.39f)
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }
        drawPath(mountains, Color(0xFF11191A).copy(alpha = 0.92f))

        drawRect(
            brush = Brush.verticalGradient(
                listOf(Color(0xFFB7834C).copy(alpha = 0.20f), Color(0xFF17100B).copy(alpha = 0.95f)),
                startY = h * 0.55f,
                endY = h
            ),
            topLeft = Offset(0f, h * 0.55f),
            size = Size(w, h * 0.45f)
        )

        val horizon = h * 0.68f
        for (i in 0..10) {
            val t = i / 10f
            val y = horizon + (h - horizon) * t * t
            drawLine(HoloGreen.copy(alpha = 0.055f + pulse * 0.025f), Offset(0f, y), Offset(w, y), 1f)
        }
        for (i in -12..12) {
            val x = w / 2f + i * w * 0.06f
            drawLine(HoloGreen.copy(alpha = 0.05f), Offset(w / 2f, horizon), Offset(x, h), 1f)
        }

        particles.forEach { p ->
            val drift = sin(scan * p.speed * 6.28f + p.phase) * 9f
            val x = p.x * w + drift
            val y = (p.y * h - scan * 36f * p.speed + h) % h
            drawCircle(HoloGreen.copy(alpha = 0.14f + pulse * 0.34f), p.radius, Offset(x, y))
        }

        val scanY = scan * h
        drawLine(HoloGreen.copy(alpha = 0.06f), Offset(0f, scanY), Offset(w, scanY), 2f)

        drawCircle(
            HoloGreen.copy(alpha = 0.025f + pulse * 0.025f),
            w * 0.30f,
            Offset(w / 2f, h * 0.48f)
        )
    }
}

@Composable
private fun CynthiaProjection(pulse: Float, rotation: Float) {
    Canvas(Modifier.width(190.dp).height(150.dp)) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val radius = size.minDimension * 0.34f

        drawCircle(HoloGreen.copy(alpha = 0.07f + pulse * 0.05f), radius * 1.55f, center)
        drawCircle(HoloGreen.copy(alpha = 0.22f), radius, center, style = Stroke(2.5f))
        drawCircle(HologramBlue.copy(alpha = 0.42f), radius * 0.78f, center, style = Stroke(1.2f))

        val angle = Math.toRadians(rotation.toDouble())
        val orbit = Offset(
            center.x + cos(angle).toFloat() * radius * 1.42f,
            center.y + sin(angle).toFloat() * radius * 0.52f
        )
        drawCircle(HoloGreenBright.copy(alpha = 0.9f), 3.5f, orbit)

        // Minimal Cynthia face silhouette: deliberately abstract and holographic.
        val face = Path().apply {
            moveTo(center.x, center.y - radius * 0.60f)
            cubicTo(
                center.x - radius * 0.55f, center.y - radius * 0.52f,
                center.x - radius * 0.48f, center.y + radius * 0.38f,
                center.x, center.y + radius * 0.68f
            )
            cubicTo(
                center.x + radius * 0.48f, center.y + radius * 0.38f,
                center.x + radius * 0.55f, center.y - radius * 0.52f,
                center.x, center.y - radius * 0.60f
            )
        }
        drawPath(face, HoloGreen.copy(alpha = 0.52f), style = Stroke(1.6f))
        drawLine(
            HoloGreenBright.copy(alpha = 0.75f),
            Offset(center.x - radius * 0.27f, center.y - radius * 0.05f),
            Offset(center.x - radius * 0.07f, center.y - radius * 0.05f),
            2f,
            StrokeCap.Round
        )
        drawLine(
            HoloGreenBright.copy(alpha = 0.75f),
            Offset(center.x + radius * 0.07f, center.y - radius * 0.05f),
            Offset(center.x + radius * 0.27f, center.y - radius * 0.05f),
            2f,
            StrokeCap.Round
        )
    }
}

@Composable
private fun SystemHeader(pulse: Float) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "CYNTHIA // LINK ESTABLISHED",
            color = HoloGreen.copy(alpha = 0.7f + pulse * 0.2f),
            fontSize = 10.sp,
            letterSpacing = 1.4.sp
        )
        Text(
            "ANDROID TRACE: NEGATIVE",
            color = SystemRed.copy(alpha = 0.65f + pulse * 0.25f),
            fontSize = 10.sp,
            letterSpacing = 1.2.sp
        )
    }
}

@Composable
private fun StoryStatusPanel(pulse: Float) {
    Box(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Panel)
            .padding(14.dp)
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text("STORY MODE", color = HologramBlue, fontSize = 9.sp, letterSpacing = 1.6.sp)
                Spacer(Modifier.height(3.dp))
                Text("CHAPTER 01 // THE ARRIVAL", color = Color.White, fontSize = 13.sp, letterSpacing = 0.7.sp)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("FORGE STATUS", color = HologramBlue, fontSize = 9.sp, letterSpacing = 1.6.sp)
                Spacer(Modifier.height(3.dp))
                Text("READY", color = HoloGreenBright.copy(alpha = 0.75f + pulse * 0.2f), fontSize = 13.sp)
            }
        }
    }
}

@Composable
private fun ForgeTile(
    modifier: Modifier,
    label: String,
    detail: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Panel)
            .clickable(onClick = onClick)
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .width(5.dp)
                    .height(28.dp)
                    .background(HoloGreen)
            )
            Spacer(Modifier.width(10.dp))
            Column {
                Text(label, color = Color.White, fontSize = 13.sp, letterSpacing = 1.5.sp)
                Spacer(Modifier.height(3.dp))
                Text(detail, color = HoloGreen.copy(alpha = 0.7f), fontSize = 8.sp, letterSpacing = 1.sp)
            }
        }
    }
}

@Composable
private fun SystemFooter(pulse: Float) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            "PERSONAFORGE DEVICE // MULTIVERSE INTERFACE",
            color = HologramBlue.copy(alpha = 0.62f),
            fontSize = 9.sp,
            letterSpacing = 1.3.sp
        )
        Spacer(Modifier.height(4.dp))
        Text(
            "CYNTHIA: \"We're clear. What shall we forge?\"",
            color = Color.White.copy(alpha = 0.65f + pulse * 0.2f),
            fontSize = 11.sp
        )
    }
}
