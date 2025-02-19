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
import androidx.compose.ui.graphics.ColorFilter
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
@Composable
fun EventDetails(
    navController: NavHostController,
    id: String? = null,
    mockEvent: EventData? = null
) {
    var event by remember { mutableStateOf(mockEvent) }

    // ✅ Fetch event details from Firebase when ID is available
    LaunchedEffect(id) {
        if (id != null) {
            val database = FirebaseDatabase.getInstance().reference.child("events").child(id)

            database.get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val fetchedEvent = snapshot.getValue(EventData::class.java)
                    if (fetchedEvent != null) {
                        event = fetchedEvent.copy(id = id) // ✅ Assign Firebase key as ID
                    }
                } else {
                    event = null // Event not found
                }
            }.addOnFailureListener {
                event = null // Handle errors
            }
        }
    }

    // UI Code (Keep this part as it was)
    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            event?.let {
                Text(text = it.name, style = TextStyle(fontWeight = FontWeight.Bold, color = Color.White, fontSize = 22.sp))
                Text(text = "${it.date} • ${it.location}", style = TextStyle(color = Color.Gray, fontSize = 14.sp))
                Text(text = it.description, style = TextStyle(color = Color.White, fontSize = 14.sp))

                Button(
                    onClick = { navController.navigate("event_list") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
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
    )

    EventDetails(navController, mockEvent = mockEvent)
}
