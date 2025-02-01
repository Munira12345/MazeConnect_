package com.example.mazeconnect


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
fun EventDetails(navController: NavHostController, eventName: String, eventDescription: String, eventDate: String, eventLocation: String, ticketPrice: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Event Name
            Text(
                text = eventName,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 22.sp
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Event Date and Location
            Text(
                text = "$eventDate • $eventLocation",
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 14.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Event Description
            BasicText(
                text = eventDescription,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )


            Button(
                onClick = { /* Handle ticket purchase */ },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Buy tickets $ticketPrice", color = Color.White)
            }


            Button(
                onClick = { /* Handle RSVP */ },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("RSVP Now", color = Color.White)
            }

            Button(
                onClick = { navController.navigate("event_list") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Back to Events", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEventDetails() {
    val navController = rememberNavController()
    EventDetails(
        navController = navController,
        eventName = "Worship",
        eventDescription = "A night of worship and praise. Join us for an uplifting experience.",
        eventDate = "December 30, 2025",
        eventLocation = "Nairobi",
        ticketPrice = "Kshs5,000"
    )
}

