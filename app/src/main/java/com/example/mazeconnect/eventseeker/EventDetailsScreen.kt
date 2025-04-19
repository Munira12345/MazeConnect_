package com.example.mazeconnect.eventseeker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import androidx.compose.foundation.Image
import com.example.mazeconnect.EventData
import android.content.Intent
import android.net.Uri
import com.example.mazeconnect.R
import java.net.URLEncoder
import androidx.compose.foundation.text.ClickableText
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import com.google.firebase.auth.FirebaseAuth


import com.google.firebase.database.FirebaseDatabase

const val RSVPButton = "RSVPButton"
const val BuyTicketButton = "BuyTicketButton"



@Composable
fun EventDetails(
    navController: NavHostController,
    id: String? = null,
    mockEvent: EventData? = null
) {
    var event by remember { mutableStateOf(mockEvent) }
    val userId = "userId123" // TODO: Replace with actual logged-in user ID
    val database = FirebaseDatabase.getInstance().reference.child("events").child(id ?: "")

    LaunchedEffect(id) {
        if (id != null) {
            database.get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val fetchedEvent = snapshot.getValue(EventData::class.java)
                    if (fetchedEvent != null) {
                        event = fetchedEvent.copy(id = id)
                    }
                } else {
                    event = null
                }
            }
        }
    }

    val isRsvped = remember { mutableStateOf(false) }

    LaunchedEffect(id) {
        if (id != null) {
            database.child("rsvps").child(userId).get().addOnSuccessListener { snapshot ->
                isRsvped.value = snapshot.exists()
            }
        }
    }

    val context = LocalContext.current
    val locationUrl = "geo:0,0?q=${URLEncoder.encode(event?.location ?: "Nairobi", "UTF-8")}"

    Spacer(modifier = Modifier.height(16.dp))
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            event?.let {
                Text(
                    text = it.name,
                    style = TextStyle(fontWeight = FontWeight.Bold, color = Color.White, fontSize = 22.sp)
                )
                Text(
                    text = "${it.date} • ${it.location}",
                    style = TextStyle(color = Color.Gray, fontSize = 14.sp)
                )
                Text(
                    text = "Category: ${it.category}",
                    style = TextStyle(color = Color.White, fontSize = 14.sp)
                )
                Text(
                    text = "Price: ${it.price}",
                    style = TextStyle(
                        color = if (it.price.lowercase() == "free") Color.Green else Color.Yellow,
                        fontSize = 14.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                ClickableText(
                    text = AnnotatedString("View Location"),
                    style = TextStyle(color = Color.Blue, textDecoration = TextDecoration.Underline),
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(locationUrl))
                        context.startActivity(intent)
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = it.description, style = TextStyle(color = Color.White, fontSize = 14.sp))

                if (it.price.lowercase() != "free") {
                    Button(
                        onClick = { /* TODO: Payment logic */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .testTag(BuyTicketButton)
                    ) {
                        Text("Buy Ticket", color = Color.White)
                    }
                }

                // RSVP
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: return@Button

                            if (id != null) {
                                val rsvpRef = database.child("rsvps").child(currentUserId)

                                if (isRsvped.value) {
                                    rsvpRef.removeValue().addOnSuccessListener {
                                        isRsvped.value = false
                                    }
                                } else {
                                    rsvpRef.setValue(true).addOnSuccessListener {
                                        isRsvped.value = true

                                        // ✅ STEP 1: Send notification to organizer
                                        val organizerId = event?.organizerId ?: return@addOnSuccessListener
                                        val notificationRef = FirebaseDatabase.getInstance().reference
                                            .child("notifications")
                                            .child(organizerId)
                                            .push()

                                        val notification = mapOf(
                                            "title" to "New RSVP",
                                            "message" to "Someone just RSVPed to your event '${event?.name}'!",
                                            "eventId" to id,
                                            "timestamp" to System.currentTimeMillis()
                                        )

                                        notificationRef.setValue(notification)
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 8.dp)
                            .testTag(RSVPButton)
                    ) {
                        Text(if (isRsvped.value) "Cancel RSVP" else "RSVP", color = Color.White)
                    }
                }

                Button(
                    onClick = { navController.navigate("event_list") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text("Back to Events", color = Color.White)
                }
            } ?: Text(
                text = "Event not available.",
                color = Color.White,
                fontSize = 16.sp
            )
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
        category = "Religious",
        price = "500 KES",
    )

    EventDetails(navController, mockEvent = mockEvent)
}