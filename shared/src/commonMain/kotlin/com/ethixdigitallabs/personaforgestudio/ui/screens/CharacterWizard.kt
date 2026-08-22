package com.ethixdigitallabs.personaforgestudio.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ethixdigitallabs.personaforgestudio.project.ProjectState

private val Cyan = Color(0xFF64DFFF)
private val White = Color(0xFFD8FAFF)
private val Lime = Color(0xFF70FF9C)
private val Red = Color(0xFFFF6262)

@Composable
fun CharacterWizard(onBack: () -> Unit = {}) {
    var characterName by remember { mutableStateOf(ProjectState.currentCharacter.name) }
    var saved by remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {
        Canvas(Modifier.fillMaxSize().background(
            Brush.verticalGradient(listOf(Color(0xFF637F82), Color(0xFF334C47), Color(0xFF18221F)))
        )) {
            val w = size.width
            val h = size.height
            for (i in 0..12) {
                val x = i * w / 12f
                drawLine(Color(0xFF142B27).copy(alpha = .55f), Offset(x, h * .62f), Offset(x, h * (.25f + (i % 4) * .05f)), 4f)
            }
            for (i in 0..10) {
                val y = h * .68f + i * h * .03f
                drawLine(Cyan.copy(alpha = .04f), Offset(0f, y), Offset(w, y), 1f)
            }
        }

        Column(
            Modifier.fillMaxSize().padding(18.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                HudText("PERSONAFORGE // CHARACTER FORGE", White, 10)
                HudText("CYNTHIA // ACTIVE", Lime, 9)
            }

            Column(
                Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HudText("REAL-TIME CHARACTER CONSTRUCTION", Cyan, 11)
                Spacer(Modifier.height(8.dp))
                HudText("Cynthia will build the character from your specifications.", White.copy(alpha = .72f), 9)
                Spacer(Modifier.height(22.dp))
                Box(Modifier.fillMaxWidth(.78f).height(2.dp).background(Cyan.copy(alpha = .55f)))
                Spacer(Modifier.height(18.dp))

                HudText("IDENTITY", Lime, 9)
                Spacer(Modifier.height(8.dp))
                BasicTextField(
                    value = characterName,
                    onValueChange = { characterName = it; saved = false },
                    singleLine = true,
                    textStyle = androidx.compose.ui.text.TextStyle(color = White, fontSize = 15.sp, letterSpacing = 1.sp),
                    modifier = Modifier
                        .fillMaxWidth(.78f)
                        .border(1.dp, Cyan.copy(alpha = .65f), CutCornerShape(8.dp))
                        .background(Color(0x33105B70))
                        .padding(14.dp)
                )
                Spacer(Modifier.height(8.dp))
                HudText("Current identity: ${characterName.ifBlank { "UNASSIGNED" }}", White.copy(alpha = .55f), 8)

                Spacer(Modifier.height(24.dp))
                HoloAction("SAVE CHARACTER", if (saved) "SAVED" else "COMMIT", Lime) {
                    ProjectState.currentCharacter.name = characterName
                    saved = true
                }
                Spacer(Modifier.height(10.dp))
                HudText("NEXT FORGE PARAMETERS: HAIR // EYES // BODY // STYLE", Cyan.copy(alpha = .65f), 8)
            }

            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                HoloAction("BACK", "CYNTHIA", Red, onBack)
                HudText("CHARACTER FORGE // ONLINE", Lime, 8)
            }
        }
    }
}

@Composable
private fun HoloAction(label: String, detail: String, accent: Color, onClick: () -> Unit) {
    Column(
        Modifier
            .background(Color(0x33105B70))
            .border(1.dp, accent.copy(alpha = .6f), CutCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HudText(label, White, 8)
        Spacer(Modifier.height(2.dp))
        HudText(detail, accent, 7)
    }
}

@Composable
private fun HudText(text: String, color: Color, size: Int) {
    Text(text, color = color, fontSize = size.sp, letterSpacing = 1.1.sp)
}
