package com.example.mazeconnect.eventorganizer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
//import androidx.compose.foundation.material3.Card
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Card
import androidx.compose.material3.ButtonDefaults
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mazeconnect.ui.theme.MazeConnectTheme
import com.example.mazeconnect.components.ReusableOutlinedButton

@Composable
fun EventMetrics(eventName: String, navController: NavHostController) {
    // Event Metrics Data
    val views = 350
    val comments = 50
    val rsvp = 100
    val shares = 25

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F7FA)) // Softer Light Green background

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
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 28.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Event Metrics Data
            MetricCard(title = "Total Views", count = views)
            MetricCard(title = "Comments", count = comments)
            MetricCard(title = "RSVPs", count = rsvp)
            MetricCard(title = "Shares", count = shares)

            // Spacer between metrics and the bottom action button
            Spacer(modifier = Modifier.weight(1f))

            // Button to Event Management screen
            ReusableOutlinedButton(
                text = "Back to Event Management",
                onClick = { navController.navigate("event_management") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

        }
    }
}

@Composable
fun MetricCard(title: String, count: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White),
        shape = RoundedCornerShape(12.dp), // Rounded corners for modern design
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
       // elevation = 4.dp // Subtle shadow effect for elevation
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Metric title
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                color = Color(0xFF333333) // Darker color for title
            )
            // Metric count
            Text(
                text = "$count",
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 36.sp, fontWeight = FontWeight.Bold),
                color = Color(0xFF6200EE), // Purple color for the count to highlight it
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

  //  EventMetrics(eventName = "Tech Conference")


@Preview(showBackground = true)
@Composable
fun PreviewEventMetrics() {
    MazeConnectTheme {
        val navController = rememberNavController()
        EventMetrics(eventName = "Tech Conference", navController = navController)
    }
}