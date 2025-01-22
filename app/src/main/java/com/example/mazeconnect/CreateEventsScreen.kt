package com.example.mazeconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mazeconnect.ui.theme.MazeConnectTheme


@Composable
fun CreateEvents(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB0E0E6)) // Light Blue background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Title for the screen
            Text(
                text = "Create New Event",
                style = TextStyle(fontSize = 24.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Event Name Field
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Event Name") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            // Event Date Field
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Event Date") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            // Event Location Field
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Event Location") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            // Event Description Field
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Event Description") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            // Image Upload Section (image picker here)
            Button(
                onClick = { /* Trigger image picker */ },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text("Select Event Image")
            }

            // Save Event Button
            Button(
                onClick = { /* Save the event logic */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .background(Color(0xFF1E90FF)) // Blue button
            ) {
                Text("Save Event", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCreateEvents() {
    MazeConnectTheme {
        val navController = rememberNavController()
        CreateEvents(navController)
    }
}

