package com.example.mazeconnect

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun EventRoleScreen() {
    // Setting up a light purple background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD0A9F5)) // Light Purple Color
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Choose your role",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 20.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Event Seeker and Event Organizer options as buttons
            Button(
                onClick = { /* Handle Event Seeker click */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Event Seeker")
            }

            Button(
                onClick = { /* Handle Event Organizer click */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Event Organizer")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEventRoleScreen() {
    EventRoleScreen()
}
