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
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color(0xFFE0F7FA)
    ) {
        val items = listOf(
            Pair(Icons.Filled.Home, "Home" to "event_organizer_home"),
            Pair(Icons.Filled.Add, "Create Event" to "create_events"),
            Pair(Icons.Filled.Settings, "Manage Events" to "event_management"),
            Pair(Icons.Filled.BarChart, "Metrics" to "event_metrics"),
            Pair(Icons.Filled.AccountCircle, "Profile" to "user_profile")
        )

        items.forEach { (icon, pair) ->
            NavigationBarItem(
                icon = { Icon(imageVector = icon, contentDescription = pair.first) },
                label = { Text(pair.first) },
                selected = false,
                onClick = { navController.navigate(pair.second) }
            )
        }
    }
}
