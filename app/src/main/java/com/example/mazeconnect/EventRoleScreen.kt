package com.example.mazeconnect

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.mazeconnect.ui.theme.MazeConnectTheme
//import androidx.compose.ui.tooling.preview.PreviewParameter
//import androidx.compose.ui.res.fontResource
import androidx.navigation.compose.rememberNavController
//import com.google.firebase.auth.FirebaseAuthException
import androidx.compose.runtime.*
import com.example.mazeconnect.SharedPrefsUtils

@Composable
fun EventRoleScreen(navController: NavHostController) {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD0A9F5)) // Light Purple Color
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Choose your role",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 20.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Button(
                onClick = {
                    saveRoleAndNavigate("EventSeeker", userId, db, context, navController)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Event Seeker")
            }

            Button(
                onClick = {
                    saveRoleAndNavigate("EventOrganizer", userId, db, context, navController)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Event Organizer")
            }
        }
    }
}

fun saveRoleAndNavigate(
    role: String,
    userId: String?,
    db: FirebaseFirestore,
    context: Context,
    navController: NavHostController
) {
    if (userId != null) {
        // Save role to Firestore
        db.collection("users").document(userId)
            .set(mapOf("role" to role))
            .addOnSuccessListener {
                // Cached roles locally
                SharedPrefsUtils.saveRole(context, role)
                // Navigate to appropriate screen
                val destination = if (role == "EventSeeker") "event_seeker_home" else "event_organizer_home"
                navController.navigate(destination) {
                    popUpTo("event_roles") { inclusive = true }
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error saving role: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    } else {
        Toast.makeText(context, "User not authenticated!", Toast.LENGTH_SHORT).show()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEventRoleScreen() {
    MazeConnectTheme {
    val navController = rememberNavController()
    EventRoleScreen(navController)
}}
