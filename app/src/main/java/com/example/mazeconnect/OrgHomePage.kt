package com.example.mazeconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.text.style.TextAlign
import com.example.mazeconnect.ui.theme.MazeConnectTheme
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.layout.ContentScale
import coil.compose.SubcomposeAsyncImage
import com.example.mazeconnect.components.BottomNavigationBar //  Import the BottomNavigationBar component
@Composable
fun OrgHomePage(navController: NavHostController) {
    // Declare the state to hold the image URL
    val profileImageUrl = remember { mutableStateOf("") }

    // Initialize the image picker launcher
    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { profileImageUrl.value = it.toString() }
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) } //imported component
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
                    // When clicked, launch the image picker
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
                            SubcomposeAsyncImage(
                                model = profileImageUrl.value,
                                contentDescription = "User Profile",
                                modifier = Modifier.size(40.dp), // Adjust size as necessary
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }

                // Horizontal Scrollable Cards (LazyRow) for the organizers content
                LazyRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val eventTitles = listOf("Your Events", "Upcoming Events", "Past Events")
                    items(eventTitles) { title ->
                        EventsCard(title)
                    }
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
