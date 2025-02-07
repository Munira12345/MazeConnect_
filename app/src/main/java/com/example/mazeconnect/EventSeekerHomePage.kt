package com.example.mazeconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.mazeconnect.components.EventSeekerBottomNavigation
//import com.example.mazeconnect.ui.theme.MazeConnectTheme
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Search
import com.google.firebase.firestore.FirebaseFirestore


data class EventData(
    val id: String = "",
    val name: String = "",
    val imageUrl: String = ""
)
@Composable
fun EventSeekerHomePage(navController: NavHostController) {
    var profileImageUrl by remember { mutableStateOf("") }
    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { profileImageUrl = it.toString() }
    }

    val events = remember { mutableStateListOf<EventData>() }

    // Fetch events from Firestore
    LaunchedEffect(Unit) {
        FirebaseFirestore.getInstance().collection("events")
            .get()
            .addOnSuccessListener { result ->
                events.clear()
                for (document in result) {
                    val event = EventData(
                        id = document.id,
                        name = document.getString("name") ?: "Untitled",
                        imageUrl = document.getString("imageUrl") ?: ""
                    )
                    events.add(event)
                }
            }
            .addOnFailureListener { e -> Log.e("Firestore", "Error fetching events", e) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .align(Alignment.TopCenter)
        ) {
            // Shortened Search Bar with icon inside
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Search...", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth(0.8f)  // Make search bar smaller
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.DarkGray),
                trailingIcon = {
                    IconButton(onClick = { /* TODO: Handle search */ }) {
                        Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color.White)
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Browse events
            Text(
                "Browse events",
                style = TextStyle(color = Color.White, fontSize = 24.sp),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )


            // Spacer to give some space between "Explore events" text and categories
            Spacer(modifier = Modifier.height(16.dp))

            // Categories
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CategoryButton("Concert", Color(0xFFFFC107))
                CategoryButton("Award", Color(0xFFAB47BC))
                CategoryButton("Music", Color(0xFF7E57C2))
            }

            // Event List
            LazyColumn {
                items(events) { event ->
                    EventCard(event)
                }
            }
        }
            // Event List
            LazyColumn {
                items(events) { event ->
                    EventCard(event)
                }
            }

        IconButton(
            onClick = { imagePicker.launch("image/*") },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            if (profileImageUrl.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(profileImageUrl),
                    contentDescription = "User Profile",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(Icons.Filled.AccountCircle, "User Profile", tint = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))



        // Bottom Navigation
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            EventSeekerBottomNavigation(navController)
        }
    }
}


@Composable
fun EventCard(event: EventData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { /* TODO: Navigate to event details */ },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Column {
            if (event.imageUrl.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(event.imageUrl),
                    contentDescription = event.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                event.name,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun CategoryButton(label: String, backgroundColor: Color) {
    Button(
        onClick = {},
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.padding(4.dp)
    ) {
        Text(label, color = Color.White)
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewEventSeekerHomePage() {
    MaterialTheme {
        val navController = rememberNavController()
        EventSeekerHomePage(navController)
    }
}
