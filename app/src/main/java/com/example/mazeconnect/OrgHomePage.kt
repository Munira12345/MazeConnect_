package com.example.mazeconnect

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import coil.compose.rememberImagePainter
import java.util.UUID
import com.example.mazeconnect.components.BottomNavigationBar // Import the BottomNavigationBar component
import com.example.mazeconnect.ui.theme.MazeConnectTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberAsyncImagePainter

@Composable
fun OrgHomePage(navController: NavHostController) {
    val user = FirebaseAuth.getInstance().currentUser
    val profileImageUrl = remember { mutableStateOf("") }
    val db = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance()
    val userId = user?.uid ?: ""  // Get the current user's UID

    // Initialize the image picker launcher
    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadImageToFirebaseStorage(it, userId) { imageUrl ->
                if (imageUrl != null) {
                    saveProfileImageUrlToFirestore(imageUrl, userId)
                    profileImageUrl.value = imageUrl
                }
            }
        }
    }

    // Fetch events from Firestore
    val events = remember { mutableStateListOf<String>() }
    val eventsLoaded = remember { mutableStateOf(false) }

    LaunchedEffect(userId) {
        db.collection("events")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                events.clear()
                for (document in querySnapshot) {
                    events.add(document.getString("eventName") ?: "Unknown Event")
                }
                eventsLoaded.value = true
            }
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE0F7FA)) // Softer Light Green background
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Icon Top Right - Implementing image picker here
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = { imagePicker.launch("image/*") }) {
                        if (profileImageUrl.value.isEmpty()) {
                            // Default account icon if no profile image is picked
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = "User Profile",
                                tint = Color.Black
                            )
                        } else {
                            // Display picked profile image
                            Image(
                                painter = rememberAsyncImagePainter(profileImageUrl.value),
                                contentDescription = "User Profile",
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                }

                // Horizontal Scrollable Cards (LazyRow) for the events
                if (eventsLoaded.value) {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(events) { event ->
                            EventsCard(title = event)
                        }
                    }
                } else {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                // Navigation Buttons
                Button(
                    onClick = { navController.navigate("create_events") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text("Create New Event", color = Color.White)
                }

                Button(
                    onClick = { navController.navigate("event_management") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text("Manage Events", color = Color.White)
                }
            }
        }
    }
}

// Function to upload the image to Firebase Storage
fun uploadImageToFirebaseStorage(uri: Uri, userId: String, onSuccess: (String?) -> Unit) {
    val storageRef = FirebaseStorage.getInstance().reference
    val fileName = UUID.randomUUID().toString()
    val profileImageRef = storageRef.child("profile_images/$userId/$fileName")

    profileImageRef.putFile(uri)
        .addOnSuccessListener {
            profileImageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                onSuccess(downloadUrl.toString())
            }
        }
        .addOnFailureListener {
            onSuccess(null)
        }
}

// Function to save the image URL to Firestore
fun saveProfileImageUrlToFirestore(imageUrl: String, userId: String) {
    val db = FirebaseFirestore.getInstance()
    val userRef = db.collection("users").document(userId)

    userRef.update("profileImageUrl", imageUrl)
        .addOnSuccessListener {
            // Image URL updated successfully
        }
        .addOnFailureListener {
            // Handle error
        }
}

@Composable
fun EventsCard(title: String) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(250.dp)
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1E90FF)), // Blue card background
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOrgHomePage() {
    MazeConnectTheme {
        val navController = rememberNavController()
        OrgHomePage(navController)
    }
}
