package com.example.mazeconnect

import EventList
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mazeconnect.ui.theme.MazeConnectTheme
import com.google.firebase.FirebaseApp
import androidx.lifecycle.lifecycleScope
import com.example.mazeconnect.SharedPrefsUtils
import kotlinx.coroutines.launch



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            MazeConnectTheme {
                AppNavigator() // Entry point to the app's navigation
            }
        }
    }
}
@Composable
fun AppNavigator() {
    val context = LocalContext.current // Correct way to access context in a Composable
    val role = remember {
        SharedPrefsUtils.getRole(context) // Fetching role using the context
    }

    val navController = rememberNavController()

    NavigationGraph(navController = navController, role = role)
}

@Composable
fun NavigationGraph(navController: NavHostController, role: String?) {
    NavHost(
        navController = navController,
        startDestination = when (role) {
            "EventSeeker" -> "event_seeker_home"
            "EventOrganizer" -> "event_organizer_home"
            else -> "sign_up" // Default start screen
        }
    ) {
        composable("sign_up") { SignUpScreen(navController) }
        composable("sign_in") { SignInScreen(navController) }
        composable("event_roles") { EventRoleScreen(navController) }
        composable("event_seeker_home") { EventSeekerHomePage(navController) }
        composable("event_organizer_home") { OrgHomePage(navController) }
        composable("create_events") { CreateEvents(navController) }
        composable("event_management") { EventManagement(navController) }
        composable("event_details/{eventName}/{eventDescription}/{eventDate}/{eventLocation}/{ticketPrice}") { backStackEntry ->
            EventDetails(
                navController,
                eventName = backStackEntry.arguments?.getString("eventName") ?: "Default Event",
                eventDescription = backStackEntry.arguments?.getString("eventDescription") ?: "No description",
                eventDate = backStackEntry.arguments?.getString("eventDate") ?: "No date",
                eventLocation = backStackEntry.arguments?.getString("eventLocation") ?: "No location",
                ticketPrice = backStackEntry.arguments?.getString("ticketPrice") ?: "Free"
            )
        }

        composable("event_list") { EventList(navController) }
       // composable("event_details") { EventDetails(navController, "Sample Event") }
       // composable("event_metrics") { EventMetrics("Sample Event") }
        composable("event_metrics") {EventMetrics("Sample Event", navController)   }
        composable("user_profile") {
            UserProfileScreen(
                userName = "Jane Doe",
                userEmail = "jane.doe@example.com",
                navController = navController    )}


        }
}
