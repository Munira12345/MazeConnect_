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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mazeconnect.ui.theme.MazeConnectTheme


@Composable
fun OrgHomePage(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFADD8E6)) // Light Blue background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Title for the screen
            Text(
                text = "Event Organizer Dashboard",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Button to create a new event
            Button(
                onClick = { /* Navigate to Create Event Screen */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .background(Color(0xFF1E90FF)) // Blue button
            ) {
                Text("Create New Event", color = Color.White)
            }


            Button(
                onClick = { /* Event Management Screen */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .background(Color(0xFF1E90FF)) // Blue button
            ) {
                Text("Manage Events", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOrgHomePage() {
    MazeConnectTheme {
        val navController = rememberNavController()
        OrgHomePage(navController)
}}
