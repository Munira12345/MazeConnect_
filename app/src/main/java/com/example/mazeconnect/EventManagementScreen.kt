package com.example.mazeconnect


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EventManagement() {
    val events = listOf(
        "Tech Conference",
        "Music Festival",
        "Art Exhibition",
        "Food Festival",
        "Startup Pitch Event"
    ) // Sample event names

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFADD8E6)) // Light Blue background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title for the screen
            Text(
                text = "Event Management",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // List of events
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(events) { event ->
                    EventManagementItem(eventName = event)
                }
            }
        }
    }
}

@Composable
fun EventManagementItem(eventName: String) {
    Button(
        onClick = { /* Navigate to Edit Event Details screen */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFF1E90FF)) // Blue button
    ) {
        Text(eventName, color = Color.White)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEventManagement() {
    EventManagement()
}
