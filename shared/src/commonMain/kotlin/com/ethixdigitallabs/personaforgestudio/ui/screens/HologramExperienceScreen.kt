package com.ethixdigitallabs.personaforgestudio.ui.screens

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
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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

private val HoloCyan = Color(0xFF6DE4FF)
private val HoloWhite = Color(0xFFD8FAFF)
private val HoloLime = Color(0xFF71FF9B)
private val HoloRed = Color(0xFFFF5D5D)
private val Glass = Color(0x421B5C72)

private data class Particle(val x: Float, val y: Float, val phase: Float)

enum class ExperienceSection(
    val title: String,
    val subtitle: String,
    val status: String
) {
    WORLD("WORLD FORGE", "CREATE PLAYABLE WORLDS", "READY"),
    STORY("STORY MODE", "CONTINUE THE INVESTIGATION", "CHAPTER 01"),
    PROJECTS("PROJECTS", "MANAGE PERSONAFORGE CREATIONS", "SYNCED"),
    INVENTORY("INVENTORY", "CHARACTER ITEMS & UNLOCKS", "READY"),
    STORE("PERSONAFORGE STORE", "EXPAND YOUR DEVICE", "ONLINE"),
    DEVICE("DEVICE SYSTEM", "CYNTHIA / SYSTEM CONTROL", "ONLINE")
}

@Composable
fun HologramExperienceScreen(
    section: ExperienceSection,
    onBack: () -> Unit
) {
    val transition = rememberInfiniteTransition(label = "experience")
    val pulse by transition.animateFloat(
        0.35f,
        1f,
        infiniteRepeatable(tween(1500), RepeatMode.Reverse),
        label = "pulse"
    )
    val scan by transition.animateFloat(
        0f,
        1f,
        infiniteRepeatable(tween(4200)),
        label = "scan"
    )
    val rotation by transition.animateFloat(
        0f,
        360f,
        infiniteRepeatable(tween(12000)),
        label = "rotation"
    )
    val particles = remember {
        List(90) { Particle(Random.nextFloat(), Random.nextFloat(), Random.nextFloat() * 6.28f) }
    }

    Box(Modifier.fillMaxSize()) {
        ExperienceEnvironment(particles, scan, pulse)
        ExperienceHud(pulse, scan)

        Column(
            Modifier.fillMaxSize().padding(18.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                HoloText("PERSONAFORGE // ${section.title}", HoloWhite, 10)
                HoloText("SYSTEM ${section.status}", HoloLime, 9)
            }

            Box(Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                CynthiaProjection(rotation, pulse)
                HoloPanel(
                    Modifier.align(Alignment.CenterStart),
                    "CYNTHIA",
                    listOf("LINK ESTABLISHED", "AI CORE READY", "USER VERIFIED"),
                    HoloCyan
                )
                HoloPanel(
                    Modifier.align(Alignment.CenterEnd),
                    "SYSTEM",
                    listOf("SIGNAL STABLE", "TRACE NEGATIVE", "RIFT ACTIVE"),
                    HoloRed
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                HoloText(section.subtitle, HoloWhite.copy(alpha = .82f), 10)
                Spacer(Modifier.height(6.dp))
                HoloText(sectionStatus(section), HoloCyan.copy(alpha = .9f), 9)
                Spacer(Modifier.height(12.dp))
                HoloCommand("BACK", "RETURN TO CYNTHIA", HoloCyan, onBack, Modifier.fillMaxWidth(.48f))
            }
        }
    }
}

private fun sectionStatus(section: ExperienceSection): String = when (section) {
    ExperienceSection.WORLD -> "WORLD ENGINE // READY FOR CREATION"
    ExperienceSection.STORY -> "INVESTIGATION // ANDROID TRACE ACTIVE"
    ExperienceSection.PROJECTS -> "PROJECT DATABASE // SYNCHRONIZED"
    ExperienceSection.INVENTORY -> "UNLOCK SYSTEM // READY"
    ExperienceSection.STORE -> "CONTENT NETWORK // CONNECTED"
    ExperienceSection.DEVICE -> "CYNTHIA CORE // FULL SYSTEM ACCESS"
}

@Composable
private fun ExperienceEnvironment(particles: List<Particle>, scan: Float, pulse: Float) {
    Canvas(Modifier.fillMaxSize().background(
        Brush.verticalGradient(
            listOf(
                Color(0xFF6D8791),
                Color(0xFF9FB0A7),
                Color(0xFF52695C),
                Color(0xFF202B27)
            )
        )
    )) {
        val w = size.width
        val h = size.height

        // Cinematic forest/mountain silhouette. This is deliberately a light
        // atmospheric layer; the final photoreal scene is supplied as an asset.
        val distant = Path().apply {
            moveTo(0f, h * .57f)
            lineTo(w * .12f, h * .39f)
            lineTo(w * .23f, h * .53f)
            lineTo(w * .37f, h * .33f)
            lineTo(w * .51f, h * .50f)
            lineTo(w * .66f, h * .36f)
            lineTo(w * .82f, h * .52f)
            lineTo(w, h * .40f)
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }
        drawPath(distant, Color(0xFF304941).copy(alpha = .72f))

        for (i in 0..13) {
            val x = i * w / 13f
            val treeHeight = h * (.18f + ((i * 37) % 7) / 40f)
            drawLine(Color(0xFF172B27).copy(alpha = .65f), Offset(x, h * .64f), Offset(x, h * .64f - treeHeight), 5f)
            drawLine(Color(0xFF172B27).copy(alpha = .65f), Offset(x, h * .53f - treeHeight * .35f), Offset(x - 22f, h * .61f - treeHeight * .12f), 3f)
            drawLine(Color(0xFF172B27).copy(alpha = .65f), Offset(x, h * .49f - treeHeight * .45f), Offset(x + 24f, h * .58f - treeHeight * .18f), 3f)
        }

        // Holographic floor depth.
        val horizon = h * .68f
        for (i in 0..10) {
            val t = i / 10f
            val y = horizon + (h - horizon) * t * t
            drawLine(HoloCyan.copy(alpha = .035f + pulse * .015f), Offset(0f, y), Offset(w, y), 1f)
        }
        for (i in -12..12) {
            val x = w / 2f + i * w * .07f
            drawLine(HoloCyan.copy(alpha = .028f), Offset(w / 2f, horizon), Offset(x, h), 1f)
        }

        particles.forEach {
            val x = it.x * w + sin(scan * 6.28f + it.phase) * 8f
            val y = (it.y * h - scan * 28f + h) % h
            drawCircle(HoloLime.copy(alpha = .12f + pulse * .22f), 1.2f, Offset(x, y))
        }
    }
}

@Composable
private fun ExperienceHud(pulse: Float, scan: Float) {
    Canvas(Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        val pad = 8f
        val corner = 34f
        val path = Path().apply {
            moveTo(pad + corner, pad)
            lineTo(w - pad - corner, pad)
            lineTo(w - pad, pad + corner)
            lineTo(w - pad, h - pad - corner)
            lineTo(w - pad - corner, h - pad)
            lineTo(pad + corner, h - pad)
            lineTo(pad, h - pad - corner)
            lineTo(pad, pad + corner)
            close()
        }
        drawPath(path, HoloCyan.copy(alpha = .78f), style = Stroke(2.2f, join = StrokeJoin.Round))
        drawPath(path, HoloCyan.copy(alpha = .12f), style = Stroke(10f))
        drawLine(HoloCyan.copy(alpha = .12f), Offset(0f, scan * h), Offset(w, scan * h), 1.5f)
        drawCircle(HoloLime.copy(alpha = .18f + pulse * .12f), 120f, Offset(w / 2f, h * .52f))
    }
}

@Composable
private fun CynthiaProjection(rotation: Float, pulse: Float) {
    Canvas(Modifier.size(270.dp, 350.dp)) {
        val c = Offset(size.width / 2f, size.height * .40f)
        drawCircle(HoloCyan.copy(alpha = .08f + pulse * .05f), 118f, c)
        drawCircle(HoloCyan.copy(alpha = .26f), 30f, Offset(c.x, c.y - 62f), style = Stroke(2.5f))

        val body = Path().apply {
            moveTo(c.x - 48f, c.y - 30f)
            lineTo(c.x - 78f, c.y + 38f)
            lineTo(c.x - 40f, c.y + 52f)
            lineTo(c.x - 27f, c.y + 142f)
            lineTo(c.x + 27f, c.y + 142f)
            lineTo(c.x + 40f, c.y + 52f)
            lineTo(c.x + 78f, c.y + 38f)
            lineTo(c.x + 48f, c.y - 30f)
            close()
        }
        drawPath(body, HoloCyan.copy(alpha = .34f), style = Stroke(2.2f, join = StrokeJoin.Round))

        for (i in -4..5) {
            val y = c.y - 90f + i * 24f
            drawLine(HoloWhite.copy(alpha = .16f), Offset(c.x - 58f, y), Offset(c.x + 58f, y), 1f)
        }

        val a = Math.toRadians(rotation.toDouble())
        drawArc(Rect(c.x - 105f, c.y - 46f, c.x + 105f, c.y + 46f), rotation, 100f, false, HoloCyan, style = Stroke(2f, cap = StrokeCap.Round))
        drawCircle(HoloLime, 4f, Offset(c.x + cos(a).toFloat() * 95f, c.y + sin(a).toFloat() * 28f))

        // Platform
        drawOval(HoloCyan.copy(alpha = .18f + pulse * .06f), Offset(c.x - 112f, size.height - 70f), androidx.compose.ui.geometry.Size(224f, 42f))
        drawOval(HoloWhite.copy(alpha = .72f), Offset(c.x - 98f, size.height - 65f), androidx.compose.ui.geometry.Size(196f, 32f), style = Stroke(2f))
        drawOval(HoloLime.copy(alpha = .5f), Offset(c.x - 62f, size.height - 58f), androidx.compose.ui.geometry.Size(124f, 18f), style = Stroke(1f))
    }
}

@Composable
private fun HoloPanel(modifier: Modifier, title: String, lines: List<String>, accent: Color) {
    Column(modifier.width(150.dp).background(Glass).padding(10.dp)) {
        HoloText(title, accent, 9)
        Spacer(Modifier.height(6.dp))
        lines.forEach { HoloText(it, HoloWhite.copy(alpha = .7f), 8) }
    }
}

@Composable
private fun HoloCommand(label: String, detail: String, accent: Color, onClick: () -> Unit, modifier: Modifier) {
    Column(
        modifier.background(Glass).clickable(onClick = onClick).padding(11.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HoloText(label, HoloWhite, 9)
        Spacer(Modifier.height(3.dp))
        HoloText(detail, accent, 7)
    }
}

@Composable
private fun HoloText(text: String, color: Color, size: Int) {
    Text(text, color = color, fontSize = size.sp, letterSpacing = 1.2.sp, fontWeight = FontWeight.Normal)
}
