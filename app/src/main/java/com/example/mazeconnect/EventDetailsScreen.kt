package com.example.mazeconnect

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.example.mazeconnect.EventData
import androidx.compose.ui.graphics.ColorFilter

@Composable
fun EventDetails(
    navController: NavHostController,
    id: String? = null,
    mockEvent: EventData? = null
) {
    var event by remember { mutableStateOf(mockEvent) }

    // Fetch event from Firebase only if it's not in preview mode
    LaunchedEffect(id) {
        if (id != null) {
            val db = FirebaseFirestore.getInstance()
            val doc = db.collection("events").document(id).get().await()
            event = doc.toObject(EventData::class.java)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Image(
            painter = painterResource(id = R.drawable.connect),
            contentDescription = "Your Image",
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.TopStart),
            colorFilter = ColorFilter.tint(Color.White)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            event?.let {
                Text(
                    text = it.name,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 22.sp
                    )
                )

                Text(
                    text = "${it.date} • ${it.location}",
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                )

                Text(
                    text = it.description,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp
                    )
                )

                Button(
                    onClick = { /* Handle ticket purchase */ },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Text("Buy Tickets")
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
}

// Preview with Mock Data
@Preview(showBackground = true)
@Composable
fun PreviewEventDetails() {
    val navController = rememberNavController()

    val mockEvent = EventData(
        id = "123",
        name = "Worship Night",
        description = "A night of worship and praise. Join us for an uplifting experience.",
        date = "December 30, 2025",
        location = "Nairobi",
    )

    EventDetails(navController, mockEvent = mockEvent)
}
