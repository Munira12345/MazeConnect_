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
import com.example.mazeconnect.components.BottomNavigationBar
import com.example.mazeconnect.ui.theme.MazeConnectTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberAsyncImagePainter
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import com.example.mazeconnect.PREFS_NAME
import com.example.mazeconnect.ORG_PROFILE_PIC_URI_KEY



@Composable
fun OrgHomePage(navController: NavHostController) {
    val user = FirebaseAuth.getInstance().currentUser
    val userId = user?.uid ?: ""


    val context = LocalContext.current
    val savedProfileImageUrl = ProfilePicSharedPrefs.getProfilePicUri(context)


    val events = remember { mutableStateListOf<String>() }
    val eventsLoaded = remember { mutableStateOf(false) }


    val subscriberCount = remember { mutableStateOf(0) }

    LaunchedEffect(userId) {

        val database = FirebaseDatabase.getInstance()
        val eventsRef = database.getReference("events")

        eventsRef.orderByChild("userId").equalTo(userId).get()
            .addOnSuccessListener { snapshot ->
                events.clear()
                snapshot.children.forEach { child ->
                    val eventName = child.child("eventName").getValue<String>()
                    if (eventName != null) {
                        events.add(eventName)
                    }
                }
                eventsLoaded.value = true
            }

        // Fetch subscriber count (will create a "subscribers" node later)
        val subscribersRef = database.getReference("subscribers")
        subscribersRef.orderByChild("orgId").equalTo(userId).get()
            .addOnSuccessListener { snapshot ->
                subscriberCount.value = snapshot.childrenCount.toInt()
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

                // Profile Picture & Subscriber Count Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f)) // Pushes items to the right

                    // Subscribers Count
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${subscriberCount.value}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "Subscribers",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Gray
                        )
                    }

                    // Profile Picture
                    IconButton(onClick = { /* Open Profile */ }) {
                        if (savedProfileImageUrl.isNullOrEmpty()) {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = "User Profile",
                                tint = Color.Black,
                                modifier = Modifier.size(40.dp)
                            )
                        } else {
                            Image(
                                painter = rememberAsyncImagePainter(savedProfileImageUrl),
                                contentDescription = "User Profile",
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                }

                // Events List
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
        ) {
            Image(
                painter = painterResource(id = R.drawable.upcomingevents),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)), // Dark overlay for readability
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
}

@Preview(showBackground = true)
@Composable
fun PreviewOrgHomePage() {
    MazeConnectTheme {
        val navController = rememberNavController()

        // Dummy event list for preview
        val dummyEvents = listOf("Tech Summit", "Business Expo", "Health Awareness", "AI Workshop")

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
                    // Profile Picture & Subscriber Count Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "0", // Default preview subscriber count
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = "Subscribers",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Light,
                                color = Color.Gray
                            )
                        }
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = "User Profile",
                                tint = Color.Black
                            )
                        }
                    }

                    // Dummy event list display
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(dummyEvents) { event ->
                            EventsCard(title = event)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Navigation Buttons
                    Button(
                        onClick = { /* Navigate to create event */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text("Create New Event", color = Color.White)
                    }

                    Button(
                        onClick = { /* Navigate to manage events */ },
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
}
