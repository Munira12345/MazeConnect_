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
import com.example.mazeconnect.components.BottomNavigationBar // Import the BottomNavigationBar component
import com.example.mazeconnect.ui.theme.MazeConnectTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberAsyncImagePainter
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.ui.platform.LocalContext
import com.example.mazeconnect.PREFS_NAME
import com.example.mazeconnect.ORG_PROFILE_PIC_URI_KEY


@Composable
fun OrgHomePage(navController: NavHostController) {
    val user = FirebaseAuth.getInstance().currentUser
    val db = FirebaseFirestore.getInstance()
    val userId = user?.uid ?: ""  // Get the current user's UID

    // Load the saved profile image URL from SharedPreferences using ProfilePicSharedPrefs
    val context = LocalContext.current
    val savedProfileImageUrl = ProfilePicSharedPrefs.getProfilePicUri(context)

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
                .background(Color(0xFFE0F7FA))
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = { /* O */ }) {
                        if (savedProfileImageUrl.isNullOrEmpty()) {

                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = "User Profile",
                                tint = Color.Black
                            )
                        } else {
                            // Display picked profile image
                            Image(
                                painter = rememberAsyncImagePainter(savedProfileImageUrl),
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



fun loadOrgProfilePicUri(sharedPreferences: SharedPreferences): String? {
    return sharedPreferences.getString(ORG_PROFILE_PIC_URI_KEY, null)
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
