package com.example.mazeconnect

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun EventList() {
    // Sample list of events
    val events = listOf("Tech Conference", "Music Festival", "Art Exhibition", "Sports Tournament")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD0A9F5)) // Light Purple Color for background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Title for the screen
            Text(
                text = "Upcoming Events",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 24.sp
                ),
                modifier = Modifier
                    .padding(16.dp)
            )

            // List of events using LazyColumn
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(events) { event ->
                    EventItem(eventName = event)
                }
            }
        }
    }
}

@Composable
fun EventItem(eventName: String) {
    Button(
        onClick = { /* Navigate to Event Details */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFF6A0DAD)) // Dark Purple Color for buttons
    ) {
        Text(eventName, color = Color.White)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEventList() {
    EventList()
}
