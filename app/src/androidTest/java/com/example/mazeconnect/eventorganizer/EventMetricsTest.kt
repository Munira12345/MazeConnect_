package com.example.mazeconnect.eventorganizer

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.*
import com.example.mazeconnect.ui.theme.MazeConnectTheme
import org.junit.Rule
import org.junit.Test

class EventMetricsTest {

    @get:Rule
    val composeTestRule = createComposeRule()



    @Test
    fun testEventMetricsScreenUI() {
        val eventName = "Tech Conference"

        composeTestRule.setContent {
            MazeConnectTheme {
                val navController = rememberNavController()


                NavHost(navController = navController, startDestination = "event_metrics") {
                    composable("event_metrics") {
                        EventMetrics(eventName = eventName, navController = navController)
                    }
                    composable("event_management") {

                    }
                }
            }
        }


        composeTestRule.onNodeWithText("Event Metrics for $eventName").assertIsDisplayed()


        composeTestRule.onNodeWithText("Total Views").assertIsDisplayed()
        composeTestRule.onNodeWithText("350").assertIsDisplayed()

        composeTestRule.onNodeWithText("Comments").assertIsDisplayed()
        composeTestRule.onNodeWithText("50").assertIsDisplayed()

        composeTestRule.onNodeWithText("RSVPs").assertIsDisplayed()
        composeTestRule.onNodeWithText("100").assertIsDisplayed()

        composeTestRule.onNodeWithText("Shares").assertIsDisplayed()
        composeTestRule.onNodeWithText("25").assertIsDisplayed()

        // Verify navigation
        composeTestRule.onNodeWithText("Back to Event Management").assertIsDisplayed()
        composeTestRule.onNodeWithText("Back to Event Management").performClick()
    }
}
