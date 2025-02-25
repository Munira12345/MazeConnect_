package com.example.mazeconnect.eventseeker

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
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.example.mazeconnect.EventData
import com.example.mazeconnect.R


@Composable
fun EventSeekerHomePage(navController: NavHostController) {
    var profileImageUrl by remember { mutableStateOf("") }
    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { profileImageUrl = it.toString() }
    }

    val events = remember { mutableStateListOf<EventData>() }

    // Fetch events from Firebase Realtime Database
    val database: DatabaseReference = FirebaseDatabase.getInstance().reference.child("events")
    LaunchedEffect(Unit) {
        database.get().addOnSuccessListener { snapshot ->
            events.clear()
            snapshot.children.forEach { childSnapshot ->
                val event = childSnapshot.getValue(EventData::class.java)
                event?.let {
                    events.add(it.copy(id = childSnapshot.key ?: "")) // Assign Firebase ID
                }
            }
        }.addOnFailureListener { e ->
            Log.e("RealtimeDB", "Error fetching events", e)
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
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

            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Search...", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
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

            Spacer(modifier = Modifier.height(16.dp))

            // Event List
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(events) { event ->
                    EventCard(event, navController)
                }
            }
        }

        // Top-right Profile Icon
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
fun EventCard(event: EventData, navController: NavController) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                if (event.id.isBlank()) {
                    Toast.makeText(context, "Event ID not available", Toast.LENGTH_SHORT).show()
                    Log.e("EventCard", "Event ID is null or empty, cannot navigate")
                } else {
                    Log.d("EventCard", "Navigating to event: ${event.id}")
                    navController.navigate("event_details/${event.id}")
                }
            },

        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.your_events),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            Text(
                text = event.name,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
                    .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
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
    val navController = rememberNavController()

    // Dummy event list for preview
    val dummyEvents = listOf(
        EventData(id = "1", name = "Tech Conference", imageUrl = ""),
        EventData(id = "2", name = "Music Festival", imageUrl = ""),
        EventData(id = "3", name = "Award Ceremony", imageUrl = "")
    )

    MaterialTheme {
        Column(modifier = Modifier.fillMaxSize().background(Color.Black).padding(16.dp)) {
            Text("Browse Events", color = Color.White, fontSize = 24.sp)

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(dummyEvents) { event ->
                    EventCard(event, navController)
                }
            }
        }
    }
}
