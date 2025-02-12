package com.example.mazeconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth

fun signOutUser(navController: NavController, onSignOutComplete: () -> Unit) {
    FirebaseAuth.getInstance().signOut()
    navController.navigate("splash_screen")
    onSignOutComplete()
}

@Composable
fun UserProfileScreen(
    navController: NavController,
    userName: String? = null,
    userEmail: String? = null
) {
    val firebaseAuth = FirebaseAuth.getInstance()
    val user = firebaseAuth.currentUser

    val displayName = userName ?: user?.displayName ?: "User"
    val email = userEmail ?: user?.email ?: "No Email"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Profile Picture Icon
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Profile Picture",
            tint = Color.Gray,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .clickable { /* Handle profile settings click later */ }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // User Name
        Text(
            text = displayName,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        // User Email
        Text(
            text = email,
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Sign Out Button
        Button(
            onClick = { signOutUser(navController) { /* Callback if needed later */ } },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A0DAD), contentColor = Color.White),
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Sign Out", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserProfileScreen() {
    val navController = rememberNavController()
    UserProfileScreen(
        navController = navController,
        userName = "Jane Doe",
        userEmail = "jane.doe@example.com"
    )
}
