package com.example.mazeconnect.feature.eventrole.ui

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mazeconnect.R
import com.example.mazeconnect.SharedPrefsUtils
import com.example.mazeconnect.feature.eventrole.viewmodel.EventRoleViewModel
import com.example.mazeconnect.feature.eventrole.viewmodel.RoleState
import com.google.firebase.auth.FirebaseAuth

@Composable
fun EventRoleScreen(
    navController: NavHostController,
    viewModel: EventRoleViewModel = viewModel()
) {
    val context = LocalContext.current
    val roleState by viewModel.roleState.collectAsState()
    val userId = FirebaseAuth.getInstance().currentUser?.uid


    LaunchedEffect(roleState) {
        when (roleState) {
            is RoleState.Success -> {
                val role = (roleState as RoleState.Success).role
                SharedPrefsUtils.saveRole(context, role)
                val destination = if (role == "EventSeeker") "event_seeker_home" else "event_organizer_home"
                navController.navigate(destination) {
                    popUpTo("event_roles") { inclusive = true }
                }
            }

            is RoleState.Error -> {
                Toast.makeText(
                    context,
                    (roleState as RoleState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> Unit
        }
    }


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
                    if (userId != null) viewModel.saveUserRole(context, "EventSeeker")

                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            RoleCard(
                title = "Event Organizer",
                description = "Create and manage amazing events.",
                icon = R.drawable.pastevents,
                borderColor = Color(0xFF008080),
                onClick = {
                    if (userId != null) viewModel.saveUserRole(context, "EventOrganizer")

                }
            )
        }
    }
}

@Composable
fun RoleCard(
    title: String,
    description: String,
    icon: Int,
    borderColor: Color,
    onClick: () -> Unit
) {
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
