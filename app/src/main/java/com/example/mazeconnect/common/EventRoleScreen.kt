package com.example.mazeconnect.common

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.mazeconnect.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.mazeconnect.SharedPrefsUtils

@Composable
fun EventRoleScreen(navController: NavHostController) {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "What role suits you?",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 24.sp
                ),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            RoleCard(
                title = "Event Seeker",
                description = "Find the best events tailored for you.",
                icon = R.drawable.upcomingevents,
                borderColor = Color(0xFF6A0DAD),
                onClick = {
                    saveRoleAndNavigate("EventSeeker", userId, db, context, navController)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            RoleCard(
                title = "Event Organizer",
                description = "Create and manage amazing events.",
                icon = R.drawable.pastevents,
                borderColor = Color(0xFF008080),
                onClick = {
                    saveRoleAndNavigate("EventOrganizer", userId, db, context, navController)
                }
            )
        }
    }
}

@Composable
fun RoleCard(title: String, description: String, icon: Int, borderColor: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .background(Color.DarkGray),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
        border = BorderStroke(2.dp, borderColor)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = borderColor,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = title, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = description, color = Color.Gray, fontSize = 14.sp)
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
        db.collection("users").document(userId)
            .set(mapOf("role" to role))
            .addOnSuccessListener {
                SharedPrefsUtils.saveRole(context, role)
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
    val navController = rememberNavController()
    EventRoleScreen(navController)
}
