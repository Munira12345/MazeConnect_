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
import com.example.mazeconnect.components.BottomNavigationBar //  Import the BottomNavigationBar component

@Composable
fun OrgHomePage(navController: NavHostController) {
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
                // Profile Icon Top Right to make functional later
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = { /* Navigate to profile screen */ }) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "User Profile",
                            tint = Color.Black
                        )
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
            .width(250.dp) // Fixed width for scrolling effect
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
