package com.example.mazeconnect

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.mazeconnect.ui.theme.MazeConnectTheme

@Composable
fun CreateEvents(navController: NavHostController) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }  // State for image URI
    var eventName by remember { mutableStateOf("") }         // State for Event Name
    var eventDate by remember { mutableStateOf("") }         // State for Event Date
    var eventLocation by remember { mutableStateOf("") }     // State for Event Location
    var eventDescription by remember { mutableStateOf("") }  // State for Event Description

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            imageUri = uri // Save selected image URI
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB0E0E6)) // Light Blue background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Title
            Text(
                text = "Create New Event",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Event Name Field
            TextField(
                value = eventName,
                onValueChange = { eventName = it },
                label = { Text("Event Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Event Date Field
            TextField(
                value = eventDate,
                onValueChange = { eventDate = it },
                label = { Text("Event Date") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Event Location Field
            TextField(
                value = eventLocation,
                onValueChange = { eventLocation = it },
                label = { Text("Event Location") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Event Description Field
            TextField(
                value = eventDescription,
                onValueChange = { eventDescription = it },
                label = { Text("Event Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Button to launch image picker
            Button(
                onClick = { imagePickerLauncher.launch("image/*") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text("Select Event Image")
            }

            // Display selected image
            imageUri?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = "Selected Event Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.Gray)
                        .padding(8.dp),
                    contentScale = ContentScale.Crop
                )
            }

            // Save Event Button
            Button(
                onClick = {
                    // Logic to save the event to the seekers home page in a card
                // and in event management list of events for the seeker
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Save Event", color = Color.White)
            }
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
