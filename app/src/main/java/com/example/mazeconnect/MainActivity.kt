package com.example.mazeconnect

import EventList
import android.os.Bundle
import android.util.Log
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
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.mazeconnect.common.EventRoleScreen
import com.example.mazeconnect.common.SignInScreen
import com.example.mazeconnect.common.SignUpScreen
import com.example.mazeconnect.common.SplashScreen
import com.example.mazeconnect.common.UserProfileScreen
import com.example.mazeconnect.eventorganizer.CreateEvents
import com.example.mazeconnect.eventorganizer.EventManagement
import com.example.mazeconnect.eventorganizer.EventMetrics
import com.example.mazeconnect.eventorganizer.OrgHomePage
import com.example.mazeconnect.eventseeker.EventDetails
import com.example.mazeconnect.eventseeker.EventSeekerHomePage


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
    val context = LocalContext.current
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
            else -> "splash_screen" // default screen
        }
    ) {
        composable("splash_screen") {
            SplashScreen(navController = navController)
        }
        composable("sign_up") { SignUpScreen(navController) }
        composable("sign_in") { SignInScreen(navController) }
        composable("event_roles") { EventRoleScreen(navController) }
        composable("event_seeker_home") { EventSeekerHomePage(navController) }
        composable("event_organizer_home") { OrgHomePage(navController) }
        composable("create_events") { CreateEvents(navController) }
        composable("event_management") { EventManagement(navController) }
        composable(
            "event_details/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")

            if (id == null) {
                Log.e("EventDetails", "Missing event ID in navigation")
                //  navigate back to homepage
                navController.popBackStack()
            } else {
                Log.d("EventDetails", "Received id: $id")
                EventDetails(navController, id)
            }
        }

        composable("event_list") { EventList(navController) }
       // composable("event_details") { EventDetails(navController, "Sample Event") }
       // composable("event_metrics") { EventMetrics("Sample Event") }
        composable("event_metrics") { EventMetrics("Sample Event", navController)   }
        composable("user_profile") {
            UserProfileScreen(navController = navController)
        }


        }
}
