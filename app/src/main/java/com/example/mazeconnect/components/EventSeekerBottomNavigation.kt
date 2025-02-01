package com.example.mazeconnect.components


import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.navigation.NavHostController

@Composable
fun EventSeekerBottomNavigation(navController: NavHostController) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color.Transparent
    ) {
        val items = listOf(
            Pair(Icons.Filled.Home, "Home" to "event_seeker_home"),
            Pair(Icons.Filled.List, "Events" to "event_list"),
            Pair(Icons.Filled.AccountCircle, "Profile" to "event_management")
        )

        items.forEach { (icon, pair) ->
            NavigationBarItem(
                icon = { Icon(imageVector = icon, contentDescription = pair.first) },
                label = { Text(pair.first) },
                selected = false,
                onClick = {
                    navController.navigate(pair.second) {
                        popUpTo(pair.second) { inclusive = true }
                    }
                }
            )
        }
    }
}
