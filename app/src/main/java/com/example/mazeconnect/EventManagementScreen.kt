package com.example.mazeconnect


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.example.mazeconnect.components.BottomNavigationBar // Import the BottomNavigationBar component
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class Event(
    val id: String = "",
    val name: String = "",
    val date: String = "",
    val location: String = "",
    val description: String = "",
    val imageUrl: String? = null
)

@Composable
fun EventManagement(navController: NavHostController) {
    val firestore = FirebaseFirestore.getInstance()
    var events by remember { mutableStateOf<List<Event>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            val snapshot = firestore.collection("events").get().await()
            events = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Event::class.java)?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) } //  Use imported component
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                //.background(Color(0xFFE0F7FA))
                .background(Color(0xFF1E90FF))
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
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navController.navigate("create_event") }
                            .padding(16.dp)
                    )
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(events) { event ->
                            EventManagementItem(eventName = event.name)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EventManagementItem(eventName: String) {
    Button(
        onClick = { /* Navigate to event details */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(eventName, color = Color.White)
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

