package com.ethixdigitallabs.personaforgestudio.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ethixdigitallabs.personaforgestudio.project.ProjectState
import com.ethixdigitallabs.personaforgestudio.ui.components.TypewriterText

@Composable
fun CharacterWizard(
    onBack: () -> Unit = {}
) {

    var characterName by remember {
        mutableStateOf(ProjectState.currentCharacter.name)
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "CYBORG CYNTHIA",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(20.dp))

            TypewriterText(
                text = "Welcome to the Forge..."
            )

            Spacer(modifier = Modifier.height(10.dp))

            TypewriterText(
                text = "Before we begin..."
            )

            Spacer(modifier = Modifier.height(10.dp))

            TypewriterText(
                text = "What is her name?"
            )

            Spacer(modifier = Modifier.height(25.dp))

            OutlinedTextField(
                value = characterName,

                onValueChange = {
                    characterName = it
                },

                label = {
                    Text("Character Name")
                }
            )

            Spacer(modifier = Modifier.height(25.dp))

            Button(

                onClick = {

                    ProjectState.currentCharacter.name = characterName

                }

            ) {

                Text("Continue")

            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Current Character: ${ProjectState.currentCharacter.name}"
            )

            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(
                onClick = onBack
            ) {

                Text("Back")

            }

        }

    }

}