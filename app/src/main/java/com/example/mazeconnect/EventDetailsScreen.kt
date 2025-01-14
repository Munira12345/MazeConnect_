package com.example.mazeconnect

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun EventDetails(eventName: String) {
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
            // Event Name Title
            Text(
                text = eventName,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 24.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Event Description
            BasicText(
                text = "Join us for an exciting day of technology discussions, workshops, and networking. The Tech Conference will feature industry leaders sharing insights on the latest trends in AI, mobile development, and more!",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Event Date (sample date)
            BasicText(
                text = "Date: February 15, 2025",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Event Location (sample location)
            BasicText(
                text = "Location: Tech Park, Nairobi",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Button to RSVP (Register for the event)
            Button(
                onClick = { /* Navigate to RSVP Screen or Show RSVP Form */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .background(Color(0xFF6A0DAD)) // Dark Purple Color for buttons
            ) {
                Text("RSVP Now", color = Color.White)
            }

            // Button to return to the Event List
            Button(
                onClick = { /* Navigate Back to Event List */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .background(Color(0xFF6A0DAD)) // Dark Purple Color for buttons
            ) {
                Text("Back to Events", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEventDetails() {
    EventDetails(eventName = "Tech Conference")
}
