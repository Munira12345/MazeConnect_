package com.example.mazeconnect.eventorganizer

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mazeconnect.ui.theme.MazeConnectTheme
import com.example.mazeconnect.components.BottomNavigationBar // BottomNavigationBar component
import com.google.firebase.database.FirebaseDatabase
import com.example.mazeconnect.components.ReusableOutlinedButton
import com.google.firebase.auth.FirebaseAuth

@Composable
fun CreateEvents(navController: NavHostController) {
    val context = LocalContext.current

    var eventName by remember { mutableStateOf("") }
    var eventDate by remember { mutableStateOf("") }
    var eventLocation by remember { mutableStateOf("") }
    var eventDescription by remember { mutableStateOf("") }
    var eventCategory by remember { mutableStateOf("") }
    var eventPrice by remember { mutableStateOf("") }
    var isUploading by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE0F7FA))
                .padding(16.dp)
                .padding(bottom = paddingValues.calculateBottomPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create New Event",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = eventName,
                onValueChange = { eventName = it },
                label = { Text("Event Name") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                singleLine = true
            )

            OutlinedTextField(
                value = eventDate,
                onValueChange = { eventDate = it },
                label = { Text("Event Date") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                singleLine = true
            )

            OutlinedTextField(
                value = eventLocation,
                onValueChange = { eventLocation = it },
                label = { Text("Event Location") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                singleLine = true
            )

            OutlinedTextField(
                value = eventDescription,
                onValueChange = { eventDescription = it },
                label = { Text("Event Description") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                singleLine = true
            )

            OutlinedTextField(
                value = eventCategory,
                onValueChange = { eventCategory = it },
                label = { Text("Event Category") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                singleLine = true
            )

            OutlinedTextField(
                value = eventPrice,
                onValueChange = { eventPrice = it },
                label = { Text("Price (Enter 'Free' or Amount)") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = {
                    isUploading = true
                    saveEventToRealtimeDatabase(
                        eventName,
                        eventDate,
                        eventLocation,
                        eventDescription,
                        eventCategory,
                        eventPrice
                    ) {
                        isUploading = false
                        Toast.makeText(context, "Event saved!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                border = BorderStroke(2.dp, Color(0xFF800080)), // Purple border
                enabled = !isUploading
            ) {
                Text(
                    text = if (isUploading) "Saving..." else "Save Event",
                    color = Color(0xFF800080) // Purple text
                )
            }

        }
    }
}

fun saveEventToRealtimeDatabase(
    name: String,
    date: String,
    location: String,
    description: String,
    category: String,
    price: String,
    onComplete: () -> Unit
) {
    val database = FirebaseDatabase.getInstance().reference
    val eventId = database.child("events").push().key

    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: "unknown"

    val event = hashMapOf(
        "name" to name,
        "date" to date,
        "location" to location,
        "description" to description,
        "category" to category,
        "price" to price,
        "organizerId" to currentUserId
    )

    eventId?.let {
        database.child("events").child(it).setValue(event)
            .addOnSuccessListener {
                Log.d("EventUpload", "Event added successfully with ID: $it")
                onComplete()
            }
            .addOnFailureListener { e ->
                Log.e("EventUpload", "Error adding event: ${e.message}")
                onComplete()
            }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCreateEvents() {
    MazeConnectTheme {
        val navController = rememberNavController()
        CreateEvents(navController)
    }
}
