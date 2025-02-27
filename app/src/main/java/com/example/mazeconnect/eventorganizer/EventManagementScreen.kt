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
@Composable
fun EventManagement(navController: NavHostController) {
    val database = FirebaseDatabase.getInstance().reference
    var events by remember { mutableStateOf<List<Event>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            val snapshot = database.child("events").get().await()
            events = snapshot.children.mapNotNull { child ->
                child.getValue(Event::class.java)?.copy(id = child.key ?: "")
            }
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
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                if (isLoading) {
                    CircularProgressIndicator()
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
                            EventManagementItem(event, onDelete = { deleteEvent(event.id) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EventManagementItem(event: Event, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
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

