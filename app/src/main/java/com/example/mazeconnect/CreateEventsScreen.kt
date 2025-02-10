package com.example.mazeconnect

import android.content.Context
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.mazeconnect.ui.theme.MazeConnectTheme
import com.example.mazeconnect.components.BottomNavigationBar // BottomNavigationBar component
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

@Composable
fun CreateEvents(navController: NavHostController) {
    val context = LocalContext.current

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var eventName by remember { mutableStateOf("") }
    var eventDate by remember { mutableStateOf("") }
    var eventLocation by remember { mutableStateOf("") }
    var eventDescription by remember { mutableStateOf("") }
    var isUploading by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE0F7FA)) // Soft Light Blue background
                .padding(16.dp)
                .padding(bottom = paddingValues.calculateBottomPadding()), // Added padding to avoid overlap
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create New Event",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp)) // Adjusted spacing

            //name
            OutlinedTextField(
                value = eventName,
                onValueChange = { eventName = it },
                label = { Text("Event Name") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                singleLine = true
            )

            // Event Date
            OutlinedTextField(
                value = eventDate,
                onValueChange = { eventDate = it },
                label = { Text("Event Date") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                singleLine = true
            )

            // Event Location
            OutlinedTextField(
                value = eventLocation,
                onValueChange = { eventLocation = it },
                label = { Text("Event Location") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                singleLine = true
            )

            // Event Description
            OutlinedTextField(
                value = eventDescription,
                onValueChange = { eventDescription = it },
                label = { Text("Event Description") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                singleLine = true
            )

            // Image Picker Button
            Button(
                onClick = { imagePickerLauncher.launch("image/*") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
            ) {
                Text("Select Event Image")
            }

            // Show Selected Image
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

            Spacer(modifier = Modifier.height(8.dp))

            // Save Event Button
            Button(
                onClick = {
                    isUploading = true
                    uploadImageAndSaveEvent(
                        context,
                        imageUri,
                        eventName,
                        eventDate,
                        eventLocation,
                        eventDescription
                    ) {
                        isUploading = false
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isUploading
            ) {
                Text(if (isUploading) "Saving..." else "Save Event", color = Color.White)
            }
        }
    }
}

// Function to upload image and save our event details
fun uploadImageAndSaveEvent(
    context: Context,
    imageUri: Uri?,
    name: String,
    date: String,
    location: String,
    description: String,
    onComplete: () -> Unit
) {
    val firestore = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance().reference

    if (imageUri != null) {
        val imageRef = storage.child("event_images/${UUID.randomUUID()}.jpg")
        imageRef.putFile(imageUri)
            .addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    saveEventToFirestore(
                        firestore,
                        name,
                        date,
                        location,
                        description,
                        downloadUrl.toString()
                    )
                    Toast.makeText(context, "Event saved!", Toast.LENGTH_SHORT).show()
                    onComplete()
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Image upload failed", Toast.LENGTH_SHORT).show()
                onComplete()
            }
    } else {
        saveEventToFirestore(firestore, name, date, location, description, null)
        Toast.makeText(context, "Event saved!", Toast.LENGTH_SHORT).show()
        onComplete()
    }
}

// Function to save event data to Firestore
fun saveEventToFirestore(
    firestore: FirebaseFirestore,
    name: String,
    date: String,
    location: String,
    description: String,
    imageUrl: String?
) {
    val event = hashMapOf(
        "name" to name,
        "date" to date,
        "location" to location,
        "description" to description,
        "imageUrl" to imageUrl
    )

    firestore.collection("events")
        .add(event)
        .addOnSuccessListener { documentReference ->
            println("Event added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            println("Error adding event: $e")
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