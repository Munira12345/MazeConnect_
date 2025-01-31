package com.example.mazeconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mazeconnect.ui.theme.MazeConnectTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.remember

import androidx.compose.ui.res.painterResource

@Composable
fun EventSeekerHomePage(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD0A9F5)) // Light Purple Background
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { /* Handle notification click */ }) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_dialog_info),
                        contentDescription = "Notifications",
                        tint = Color.Black
                    )
                }
            }

            Text(
                text = "Welcome, Event Seeker!",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 28.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Upcoming Events",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(listOf("Event 1", "Event 2", "Event 3")) { event ->
                    EventCard(event)
                }
            }

            Text(
                text = "Past Events",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(listOf("Past Event 1", "Past Event 2", "Past Event 3")) { event ->
                    EventCard(event)
                }
            }

            Spacer(modifier = Modifier.weight(0.5f))

            Button(
                onClick = { /* Sign Out the User */ },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6A0DAD), // Dark Purple
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Sign Out", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun EventCard(eventName: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Text(
            text = eventName,
            modifier = Modifier.padding(16.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEventSeekerHomePage() {
    MazeConnectTheme {
        val navController = rememberNavController()
        EventSeekerHomePage(navController)
    }}
