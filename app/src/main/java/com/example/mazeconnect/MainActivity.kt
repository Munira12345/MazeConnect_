package com.example.mazeconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mazeconnect.ui.theme.MazeConnectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MazeConnectTheme {
                AppNavigator() // Entry point to the app's navigation
            }
        }
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavigationGraph(navController)
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "sign_up"
    ) {
        composable("sign_up") { SignUpScreen(navController) }
        composable("sign_in") { SignInScreen(navController) }
       /* composable("event_roles") { EventRoleScreen(navController) }
        composable("event_seeker_home") { EventSeekerHomePage(navController) }
        composable("event_list") { EventList(navController) }
        composable("event_details") { EventDetails(navController, "Sample Event") }
        composable("event_organizer_home") { OrgHomePage(navController) }
        composable("create_events") { CreateEvents(navController) }
        composable("event_management") { EventManagement(navController) }
        composable("event_metrics") { EventMetrics("Sample Event") }

        */
    }
}
