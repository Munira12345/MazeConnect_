package com.example.mazeconnect


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EventSeekerHomePage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD0A9F5)) // Light Purple Color for background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Text(
                text = "Welcome, Event Seeker!",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 24.sp
                ),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Button to view upcoming events
            Button(
                onClick = { /* Navigate to Event List */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .background(Color(0xFF6A0DAD)) // Dark Purple Color
            ) {
                Text("View Upcoming Events", color = Color.White)
            }

            // Button to view past events
            Button(
                onClick = { /* Navigate to Past Events List */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .background(Color(0xFF6A0DAD)) // Dark Purple Color
            ) {
                Text("View Past Events", color = Color.White)
            }

            // Button to edit profile
            Button(
                onClick = { /* Navigate to Edit Profile Screen */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .background(Color(0xFF6A0DAD)) // Dark Purple Color
            ) {
                Text("Edit Profile", color = Color.White)
            }

            // Button to sign out
            Button(
                onClick = { /* Sign Out the User */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .background(Color(0xFF6A0DAD)) // Dark Purple Color
            ) {
                Text("Sign Out", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEventSeekerHomePage() {
    EventSeekerHomePage()
}
