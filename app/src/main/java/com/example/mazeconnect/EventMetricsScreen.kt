package com.example.mazeconnect

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EventMetrics(eventName: String) {

    val registered = 120
    val views = 350
    val comments = 50
    val rsvp = 100
    val shares = 25

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
                text = "Event Metrics for $eventName",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Event Metrics Data
            MetricCard(title = "Registrations", count = registered)
            MetricCard(title = "Views", count = views)
            MetricCard(title = "Comments", count = comments)
            MetricCard(title = "RSVPs", count = rsvp)
            MetricCard(title = "Shares", count = shares)

            // Spacer between metrics and the bottom action button
            Spacer(modifier = Modifier.weight(1f))

            // Button to go back to the Event Management screen
            Button(
                onClick = { /* Navigate back to Event Management or another screen */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color(0xFF1E90FF)) // Blue button
            ) {
                Text(text = "Back to Event Management", color = Color.White)
            }
        }
    }
}

@Composable
fun MetricCard(title: String, count: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White),
       // elevation = CardDefaults.elevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
            Text(
                text = "$count",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEventMetrics() {
    EventMetrics(eventName = "Tech Conference")
}
