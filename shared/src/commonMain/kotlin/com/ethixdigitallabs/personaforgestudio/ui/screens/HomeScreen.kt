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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

private val Cyan = Color(0xFF62D8FF)
private val CyanBright = Color(0xFFB9F2FF)
private val Blue = Color(0xFF248CFF)
private val Lime = Color(0xFF69FF9A)
private val Red = Color(0xFFFF4A4A)
private val Glass = Color(0xB80A1820)
private val GlassLight = Color(0x5038BFE8)

/**
 * Environment presented behind the PersonaForge HUD.
 * CINEMATIC is the default product environment. CLOSING_SCENE is an optional
 * visual mode reserved for the closing-scene environment asset when supplied.
 */
enum class PersonaForgeEnvironment {
    CINEMATIC,
    CLOSING_SCENE
}

@Composable
fun HomeScreen(
    onCreateCharacter: () -> Unit = {},
    onCreateAdventure: () -> Unit = {},
    onOpenProject: () -> Unit = {},
    onSettings: () -> Unit = {},
    environment: PersonaForgeEnvironment = PersonaForgeEnvironment.CINEMATIC
) {
    val transition = rememberInfiniteTransition(label = "persona_forge_hud")
    val pulse by transition.animateFloat(
        initialValue = 0.35f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1700), RepeatMode.Reverse),
        label = "pulse"
    )
    val scan by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(4200)),
        label = "scan"
    )
    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(14000)),
        label = "rotation"
    )

    val particles = remember {
        List(110) {
            HoloParticle(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                radius = 0.7f + Random.nextFloat() * 2.2f,
                phase = Random.nextFloat() * 6.283f,
                speed = 0.3f + Random.nextFloat() * 1.4f
            )
        }
    }

    Box(Modifier.fillMaxSize()) {
        CinematicEnvironment(
            environment = environment,
            particles = particles,
            pulse = pulse,
            scan = scan
        )

        HologramAtmosphere(pulse = pulse, scan = scan)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            HudHeader(pulse)

            Box(Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                // Cynthia remains the focal point. The current implementation is
                // deliberately an animated holographic projection shell; a real
                // character render can be supplied later without changing the HUD.
                CynthiaProjection(pulse = pulse, rotation = rotation)

                HudPanel(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(top = 20.dp),
                    title = "PERSONA FORGE",
                    lines = listOf("DEVICE ONLINE", "MULTIVERSE LINK", "FORGE READY"),
                    accent = Cyan
                )

                HudPanel(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(top = 20.dp),
                    title = "TRACE",
                    lines = listOf("ANDROID: NEGATIVE", "SIGNAL: STABLE", "RIFT: ACTIVE"),
                    accent = Red
                )

                HologramPlatform(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    rotation = rotation,
                    pulse = pulse
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "CYNTHIA // LINK ESTABLISHED",
                    color = CyanBright.copy(alpha = 0.75f + pulse * 0.2f),
                    fontSize = 10.sp,
                    letterSpacing = 2.sp
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    "\"We're clear. What shall we forge?\"",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )
                Spacer(Modifier.height(14.dp))

                HologramCommandRail(
                    onCreateCharacter = onCreateCharacter,
                    onCreateAdventure = onCreateAdventure,
                    onOpenProject = onOpenProject,
                    onSettings = onSettings
                )

                Spacer(Modifier.height(8.dp))
                Text(
                    "PERSONAFORGE // STORY MODE // CHAPTER 01",
                    color = Lime.copy(alpha = 0.55f + pulse * 0.15f),
                    fontSize = 8.sp,
                    letterSpacing = 1.5.sp
                )
            }
        }
    }
}

private data class HoloParticle(
    val x: Float,
    val y: Float,
    val radius: Float,
    val phase: Float,
    val speed: Float
)

@Composable
private fun CinematicEnvironment(
    environment: PersonaForgeEnvironment,
    particles: List<HoloParticle>,
    pulse: Float,
    scan: Float
) {
    Canvas(
        Modifier.fillMaxSize().background(
            Brush.verticalGradient(
                when (environment) {
                    PersonaForgeEnvironment.CINEMATIC -> listOf(
                        Color(0xFF15262A), Color(0xFF354B43), Color(0xFF8B6D49), Color(0xFF1B1713)
                    )
                    PersonaForgeEnvironment.CLOSING_SCENE -> listOf(
                        Color(0xFF071419), Color(0xFF102B2B), Color(0xFF25362F), Color(0xFF0A1110)
                    )
                }
            )
        )
    ) {
        val w = size.width
        val h = size.height

        // Layered cinematic terrain: intentionally subordinate to the HUD.
        val terrain = Path().apply {
            moveTo(0f, h * 0.62f)
            when (environment) {
                PersonaForgeEnvironment.CINEMATIC -> {
                    lineTo(w * 0.13f, h * 0.43f)
                    lineTo(w * 0.26f, h * 0.55f)
                    lineTo(w * 0.39f, h * 0.35f)
                    lineTo(w * 0.53f, h * 0.51f)
                    lineTo(w * 0.68f, h * 0.39f)
                    lineTo(w * 0.82f, h * 0.53f)
                    lineTo(w, h * 0.41f)
                }
                PersonaForgeEnvironment.CLOSING_SCENE -> {
                    lineTo(w * 0.12f, h * 0.50f)
                    lineTo(w * 0.23f, h * 0.35f)
                    lineTo(w * 0.32f, h * 0.53f)
                    lineTo(w * 0.46f, h * 0.32f)
                    lineTo(w * 0.61f, h * 0.50f)
                    lineTo(w * 0.76f, h * 0.36f)
                    lineTo(w, h * 0.51f)
                }
            }
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }
        drawPath(terrain, Color.Black.copy(alpha = 0.32f))

        val horizon = h * 0.69f
        for (i in 0..12) {
            val t = i / 12f
            val y = horizon + (h - horizon) * t * t
            drawLine(Cyan.copy(alpha = 0.035f + pulse * 0.018f), Offset(0f, y), Offset(w, y), 1f)
        }
        for (i in -14..14) {
            val x = w / 2f + i * w * 0.055f
            drawLine(Cyan.copy(alpha = 0.028f), Offset(w / 2f, horizon), Offset(x, h), 1f)
        }

        particles.forEach { particle ->
            val drift = sin(scan * particle.speed * 6.283f + particle.phase) * 8f
            val x = particle.x * w + drift
            val y = (particle.y * h - scan * 30f * particle.speed + h) % h
            drawCircle(
                Lime.copy(alpha = 0.08f + pulse * 0.28f),
                particle.radius,
                Offset(x, y)
            )
        }

        drawLine(
            Cyan.copy(alpha = 0.08f),
            Offset(0f, scan * h),
            Offset(w, scan * h),
            1.5f
        )
    }
}

@Composable
private fun HologramAtmosphere(pulse: Float, scan: Float) {
    Canvas(Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        drawRect(
            Brush.radialGradient(
                listOf(Cyan.copy(alpha = 0.09f + pulse * 0.035f), Color.Transparent),
                center = Offset(w / 2f, h * 0.58f),
                radius = w * 0.62f
            )
        )
        drawRect(Cyan.copy(alpha = 0.018f), topLeft = Offset(0f, scan * h), size = androidx.compose.ui.geometry.Size(w, 2f))
    }
}

@Composable
private fun CynthiaProjection(pulse: Float, rotation: Float) {
    Canvas(Modifier.size(width = 250.dp, height = 330.dp)) {
        val center = Offset(size.width / 2f, size.height * 0.43f)
        val bodyHeight = size.height * 0.62f
        val glow = 0.18f + pulse * 0.12f

        drawCircle(Cyan.copy(alpha = glow), 88f, center)

        // Head and shoulders: intentionally a projection silhouette, not a fake
        // photoreal character. This keeps the architecture ready for a real model.
        drawCircle(CyanBright.copy(alpha = 0.52f), 28f, Offset(center.x, center.y - 58f), style = Stroke(2.2f))

        val body = Path().apply {
            moveTo(center.x - 46f, center.y - 28f)
            lineTo(center.x - 70f, center.y + 30f)
            lineTo(center.x - 38f, center.y + 42f)
            lineTo(center.x - 30f, center.y + bodyHeight * 0.44f)
            lineTo(center.x - 10f, center.y + bodyHeight * 0.44f)
            lineTo(center.x, center.y + bodyHeight * 0.10f)
            lineTo(center.x + 10f, center.y + bodyHeight * 0.44f)
            lineTo(center.x + 30f, center.y + bodyHeight * 0.44f)
            lineTo(center.x + 38f, center.y + 42f)
            lineTo(center.x + 70f, center.y + 30f)
            lineTo(center.x + 46f, center.y - 28f)
        }
        drawPath(body, Cyan.copy(alpha = 0.48f), style = Stroke(2f, join = StrokeJoin.Round))

        for (i in -3..3) {
            val y = center.y - 80f + i * 22f
            drawLine(CyanBright.copy(alpha = 0.18f), Offset(center.x - 55f, y), Offset(center.x + 55f, y), 1f)
        }

        val orbit = Math.toRadians(rotation.toDouble())
        val ringPoint = Offset(
            center.x + cos(orbit).toFloat() * 92f,
            center.y + sin(orbit).toFloat() * 32f
        )
        drawCircle(Lime.copy(alpha = 0.9f), 3.5f, ringPoint)

        drawArc(
            Rect(center.x - 98f, center.y - 42f, center.x + 98f, center.y + 42f),
            startAngle = rotation,
            sweepAngle = 105f,
            useCenter = false,
            color = CyanBright.copy(alpha = 0.75f),
            style = Stroke(2f, cap = StrokeCap.Round)
        )
    }
}

@Composable
private fun HologramPlatform(modifier: Modifier, rotation: Float, pulse: Float) {
    Canvas(modifier.size(width = 250.dp, height = 80.dp)) {
        val c = Offset(size.width / 2f, size.height * 0.55f)
        drawOval(
            Cyan.copy(alpha = 0.12f + pulse * 0.06f),
            topLeft = Offset(c.x - 105f, c.y - 20f),
            size = androidx.compose.ui.geometry.Size(210f, 40f)
        )
        drawOval(
            CyanBright.copy(alpha = 0.65f),
            topLeft = Offset(c.x - 92f, c.y - 16f),
            size = androidx.compose.ui.geometry.Size(184f, 32f),
            style = Stroke(2f)
        )
        drawOval(
            Lime.copy(alpha = 0.5f),
            topLeft = Offset(c.x - 62f, c.y - 10f),
            size = androidx.compose.ui.geometry.Size(124f, 20f),
            style = Stroke(1f)
        )
        val a = Math.toRadians(rotation.toDouble())
        drawCircle(Lime, 3f, Offset(c.x + cos(a).toFloat() * 84f, c.y + sin(a).toFloat() * 12f))
    }
}

@Composable
private fun HudHeader(pulse: Float) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HudText("CYNTHIA // PERSONAFORGE", CyanBright.copy(alpha = 0.8f + pulse * 0.15f), 10)
        HudText("ANDROID TRACE // NEGATIVE", Red.copy(alpha = 0.65f + pulse * 0.2f), 9)
    }
}

@Composable
private fun HudPanel(
    modifier: Modifier,
    title: String,
    lines: List<String>,
    accent: Color
) {
    Column(
        modifier = modifier
            .width(150.dp)
            .background(Glass)
            .padding(12.dp)
    ) {
        Canvas(Modifier.fillMaxWidth().height(8.dp)) {
            drawLine(accent.copy(alpha = 0.8f), Offset(0f, 1f), Offset(size.width * 0.72f, 1f), 1.5f)
            drawLine(accent.copy(alpha = 0.35f), Offset(size.width * 0.82f, 1f), Offset(size.width, 1f), 1f)
        }
        HudText(title, accent, 9)
        Spacer(Modifier.height(6.dp))
        lines.forEach { HudText(it, Color.White.copy(alpha = 0.72f), 8) }
    }
}

@Composable
private fun HologramCommandRail(
    onCreateCharacter: () -> Unit,
    onCreateAdventure: () -> Unit,
    onOpenProject: () -> Unit,
    onSettings: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        HoloCommand("CHARACTER", "FORGE", Cyan, onCreateCharacter, Modifier.weight(1f))
        HoloCommand("WORLD", "FORGE", Cyan, onCreateAdventure, Modifier.weight(1f))
        HoloCommand("PROJECTS", "OPEN", Lime, onOpenProject, Modifier.weight(1f))
        HoloCommand("DEVICE", "SYSTEM", Red, onSettings, Modifier.weight(1f))
    }
}

@Composable
private fun HoloCommand(
    label: String,
    detail: String,
    accent: Color,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .background(GlassLight)
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp, horizontal = 7.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(label, color = Color.White, fontSize = 8.sp, letterSpacing = 1.1.sp)
        Spacer(Modifier.height(3.dp))
        Text(detail, color = accent, fontSize = 7.sp, letterSpacing = 1.2.sp)
    }
}

@Composable
private fun HudText(text: String, color: Color, size: Int) {
    Text(text, color = color, fontSize = size.sp, letterSpacing = 1.15.sp)
}
