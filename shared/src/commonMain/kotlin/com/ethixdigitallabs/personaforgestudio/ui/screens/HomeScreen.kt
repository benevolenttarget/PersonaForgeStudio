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

private val Cyan = Color(0xFF64DFFF)
private val White = Color(0xFFD8FAFF)
private val Lime = Color(0xFF70FF9C)
private val Red = Color(0xFFFF6262)
private val Glass = Color(0x3A0B4255)

/** The visual source of truth is the cinematic HUD: environment first, HUD second. */
enum class PersonaForgeEnvironment { CINEMATIC, CLOSING_SCENE }

@Composable
fun HomeScreen(
    onCreateCharacter: () -> Unit = {},
    onCreateAdventure: () -> Unit = {},
    onOpenProject: () -> Unit = {},
    onSettings: () -> Unit = {},
    onStoryMode: () -> Unit = {},
    onInventory: () -> Unit = {},
    onStore: () -> Unit = {},
    environment: PersonaForgeEnvironment = PersonaForgeEnvironment.CINEMATIC
) {
    val transition = rememberInfiniteTransition(label = "persona_hud")
    val pulse by transition.animateFloat(.35f, 1f, infiniteRepeatable(tween(1500), RepeatMode.Reverse), label = "pulse")
    val scan by transition.animateFloat(0f, 1f, infiniteRepeatable(tween(4200)), label = "scan")
    val rotation by transition.animateFloat(0f, 360f, infiniteRepeatable(tween(12000)), label = "rotation")
    val particles = remember { List(120) { HoloParticle(Random.nextFloat(), Random.nextFloat(), Random.nextFloat() * 6.28f) } }

    Box(Modifier.fillMaxSize()) {
        CinematicEnvironment(environment, particles, scan, pulse)
        HudFrame(pulse, scan)

        Column(
            Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                HudText("PERSONAFORGE // CYNTHIA", White, 10)
                HudText("ANDROID TRACE // NEGATIVE", Red, 9)
            }

            Box(Modifier.fillMaxWidth().weight(1f), Alignment.Center) {
                CynthiaProjection(rotation, pulse)

                HudPanel(
                    Modifier.align(Alignment.CenterStart).padding(start = 4.dp),
                    "PERSONAFORGE DEVICE",
                    listOf("ONLINE", "RIFT LINK ACTIVE", "FORGE READY"), Cyan
                )
                HudPanel(
                    Modifier.align(Alignment.CenterEnd).padding(end = 4.dp),
                    "INVESTIGATION",
                    listOf("TRACE NEGATIVE", "SIGNAL STABLE", "CHAPTER 01"), Red
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                HudText("CYNTHIA // LINK ESTABLISHED", Cyan.copy(alpha = .8f + pulse * .15f), 10)
                Spacer(Modifier.height(4.dp))
                Text(
                    "\"We're clear. What shall we forge?\"",
                    color = White.copy(alpha = .92f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )
                Spacer(Modifier.height(10.dp))

                // Holographic controls: no Material buttons and no opaque cards.
                Row(Modifier.fillMaxWidth(), Arrangement.spacedBy(5.dp)) {
                    HoloCommand("CHARACTER", "FORGE", Cyan, onCreateCharacter, Modifier.weight(1f))
                    HoloCommand("WORLD", "FORGE", Cyan, onCreateAdventure, Modifier.weight(1f))
                    HoloCommand("STORY", "MODE", Lime, onStoryMode, Modifier.weight(1f))
                    HoloCommand("PROJECTS", "OPEN", Lime, onOpenProject, Modifier.weight(1f))
                }
                Spacer(Modifier.height(5.dp))
                Row(Modifier.fillMaxWidth(), Arrangement.spacedBy(5.dp)) {
                    HoloCommand("INVENTORY", "UNLOCKS", Cyan, onInventory, Modifier.weight(1f))
                    HoloCommand("STORE", "ONLINE", Lime, onStore, Modifier.weight(1f))
                    HoloCommand("DEVICE", "SYSTEM", Red, onSettings, Modifier.weight(1f))
                }
                Spacer(Modifier.height(7.dp))
                HudText("PERSONAFORGE // STORY MODE // CHAPTER 01", Lime.copy(alpha = .72f), 8)
            }
        }
    }
}

private data class HoloParticle(val x: Float, val y: Float, val phase: Float)

@Composable
private fun CinematicEnvironment(
    environment: PersonaForgeEnvironment,
    particles: List<HoloParticle>,
    scan: Float,
    pulse: Float
) {
    Canvas(Modifier.fillMaxSize().background(
        Brush.verticalGradient(
            if (environment == PersonaForgeEnvironment.CLOSING_SCENE) {
                listOf(Color(0xFF78949A), Color(0xFFB7C0B6), Color(0xFF5A705F), Color(0xFF202824))
            } else {
                listOf(Color(0xFF667F7F), Color(0xFF9BAEA1), Color(0xFF596B59), Color(0xFF252A23))
            }
        )
    )) {
        val w = size.width
        val h = size.height

        // Forest/mountain silhouette matching the cinematic composition. The real
        // photoreal scene is intentionally an asset slot rather than a fake gradient.
        val mountains = Path().apply {
            moveTo(0f, h * .60f)
            lineTo(w * .12f, h * .42f)
            lineTo(w * .24f, h * .55f)
            lineTo(w * .38f, h * .34f)
            lineTo(w * .52f, h * .51f)
            lineTo(w * .67f, h * .38f)
            lineTo(w * .83f, h * .52f)
            lineTo(w, h * .41f)
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }
        drawPath(mountains, Color(0xFF31463E).copy(alpha = .72f))

        for (i in 0..16) {
            val x = i * w / 16f
            val top = h * (.25f + ((i * 17) % 9) / 35f)
            drawLine(Color(0xFF1B302A).copy(alpha = .62f), Offset(x, h * .64f), Offset(x, top), 4f)
            drawLine(Color(0xFF1B302A).copy(alpha = .55f), Offset(x, top + 30f), Offset(x - 22f, top + 70f), 2.5f)
            drawLine(Color(0xFF1B302A).copy(alpha = .55f), Offset(x, top + 42f), Offset(x + 24f, top + 82f), 2.5f)
        }

        val horizon = h * .69f
        for (i in 0..11) {
            val t = i / 11f
            val y = horizon + (h - horizon) * t * t
            drawLine(Cyan.copy(alpha = .025f + pulse * .012f), Offset(0f, y), Offset(w, y), 1f)
        }
        for (i in -13..13) {
            drawLine(Cyan.copy(alpha = .022f), Offset(w / 2f, horizon), Offset(w / 2f + i * w * .065f, h), 1f)
        }

        particles.forEach {
            val x = it.x * w + sin(scan * 6.28f + it.phase) * 9f
            val y = (it.y * h - scan * 30f + h) % h
            drawCircle(Lime.copy(alpha = .08f + pulse * .24f), 1.5f, Offset(x, y))
        }
    }
}

@Composable
private fun HudFrame(pulse: Float, scan: Float) {
    Canvas(Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        val p = 7f
        val c = 30f
        val frame = Path().apply {
            moveTo(p + c, p); lineTo(w - p - c, p); lineTo(w - p, p + c)
            lineTo(w - p, h - p - c); lineTo(w - p - c, h - p); lineTo(p + c, h - p)
            lineTo(p, h - p - c); lineTo(p, p + c); close()
        }
        drawPath(frame, Cyan.copy(alpha = .82f), style = Stroke(2.4f, join = StrokeJoin.Round))
        drawPath(frame, Cyan.copy(alpha = .10f + pulse * .03f), style = Stroke(10f))
        drawLine(Cyan.copy(alpha = .10f), Offset(0f, scan * h), Offset(w, scan * h), 1.5f)
    }
}

@Composable
private fun CynthiaProjection(rotation: Float, pulse: Float) {
    Canvas(Modifier.size(270.dp, 350.dp)) {
        val c = Offset(size.width / 2f, size.height * .40f)
        drawCircle(Cyan.copy(alpha = .10f + pulse * .06f), 120f, c)
        drawCircle(White.copy(alpha = .55f), 29f, Offset(c.x, c.y - 62f), style = Stroke(2.4f))
        val body = Path().apply {
            moveTo(c.x - 48f, c.y - 30f); lineTo(c.x - 78f, c.y + 38f); lineTo(c.x - 40f, c.y + 52f)
            lineTo(c.x - 28f, c.y + 144f); lineTo(c.x + 28f, c.y + 144f); lineTo(c.x + 40f, c.y + 52f)
            lineTo(c.x + 78f, c.y + 38f); lineTo(c.x + 48f, c.y - 30f); close()
        }
        drawPath(body, Cyan.copy(alpha = .38f), style = Stroke(2.2f, join = StrokeJoin.Round))
        for (i in -4..5) {
            val y = c.y - 88f + i * 24f
            drawLine(White.copy(alpha = .15f), Offset(c.x - 60f, y), Offset(c.x + 60f, y), 1f)
        }
        val a = Math.toRadians(rotation.toDouble())
        drawArc(Rect(c.x - 105f, c.y - 45f, c.x + 105f, c.y + 45f), rotation, 105f, false, Cyan, style = Stroke(2f, cap = StrokeCap.Round))
        drawCircle(Lime, 4f, Offset(c.x + cos(a).toFloat() * 96f, c.y + sin(a).toFloat() * 28f))

        val py = size.height - 56f
        drawOval(Cyan.copy(alpha = .20f + pulse * .07f), Offset(c.x - 112f, py), androidx.compose.ui.geometry.Size(224f, 42f))
        drawOval(White.copy(alpha = .75f), Offset(c.x - 98f, py + 5f), androidx.compose.ui.geometry.Size(196f, 32f), style = Stroke(2f))
        drawOval(Lime.copy(alpha = .48f), Offset(c.x - 62f, py + 12f), androidx.compose.ui.geometry.Size(124f, 18f), style = Stroke(1f))
    }
}

@Composable
private fun HudPanel(modifier: Modifier, title: String, lines: List<String>, accent: Color) {
    Column(modifier.width(148.dp).background(Glass).padding(10.dp)) {
        HudText(title, accent, 9)
        Spacer(Modifier.height(5.dp))
        lines.forEach { HudText(it, White.copy(alpha = .70f), 8) }
    }
}

@Composable
private fun HoloCommand(label: String, detail: String, accent: Color, onClick: () -> Unit, modifier: Modifier) {
    Column(
        modifier.background(Glass).clickable(onClick = onClick).padding(vertical = 9.dp, horizontal = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HudText(label, White, 8)
        Spacer(Modifier.height(2.dp))
        HudText(detail, accent, 7)
    }
}

@Composable
private fun HudText(text: String, color: Color, size: Int) {
    Text(text, color = color, fontSize = size.sp, letterSpacing = 1.15.sp)
}
