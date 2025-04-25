package com.example.mazeconnect.eventorganizer


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mazeconnect.ui.theme.MazeConnectTheme
import com.example.mazeconnect.components.BottomNavigationBar
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await


data class Event(
    val id: String = "",
    val name: String = "",
    val date: String = "",
    val location: String = "",
    val description: String = ""
)
const val LOADING_INDICATOR_TAG = "loading_indicator"
const val EVENT_MANAGEMENT = "EVENT_MANAGEMENT"

@Composable
fun EventManagement(navController: NavHostController, initialEvents: List<Event> = emptyList()) {
    val database = FirebaseDatabase.getInstance().reference
    var events by remember { mutableStateOf(initialEvents) } // Use initialEvents properly
    var isLoading by remember { mutableStateOf(events.isEmpty()) }
    var rsvpCounts by remember { mutableStateOf<Map<String, Int>>(emptyMap()) }


    LaunchedEffect(Unit) {
        try {
            val snapshot = database.child("events").get().await()
            val loadedEvents = snapshot.children.mapNotNull { child ->
                child.getValue(Event::class.java)?.copy(id = child.key ?: "")
            }
            events = loadedEvents

            // Fetch RSVP counts for each event
            val rsvpMap = mutableMapOf<String, Int>()
            loadedEvents.forEach { event ->
                val rsvpSnapshot = database.child("events").child(event.id).child("rsvps").get().await()
                rsvpMap[event.id] = rsvpSnapshot.childrenCount.toInt()
            }
            rsvpCounts = rsvpMap

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    fun deleteEvent(eventId: String) {
        database.child("events").child(eventId).removeValue()
            .addOnSuccessListener {
                events = events.filter { it.id != eventId } // Updating UI after deletion
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE0F7FA))
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Event Management",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 24.dp).testTag(EVENT_MANAGEMENT)
                )

                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.testTag(LOADING_INDICATOR_TAG))
                } else if (events.isEmpty()) {
                    Text(
                        text = "No events. Click to create event.",
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navController.navigate("create_event") }
                            .padding(16.dp)
                    )
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(events) { event ->
                            val count = rsvpCounts[event.id] ?: 0
                            EventManagementItem(event, count, onDelete = { deleteEvent(event.id) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EventManagementItem(event: Event, rsvpCount: Int, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = event.name,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyLarge
                )
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Event",
                        tint = Color.Red
                    )
                }
            }
            Text(
                text = "RSVPs: $rsvpCount",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewEventManagement() {
    MazeConnectTheme {
        val navController = rememberNavController()
        EventManagement(navController)
    }
}

