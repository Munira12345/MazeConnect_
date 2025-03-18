package com.example.mazeconnect.eventorganizer

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.mazeconnect.ui.theme.MazeConnectTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CreateEventsTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MazeConnectTheme {
                val navController = rememberNavController()
                CreateEvents(navController)
            }
        }
    }

    @Test
    fun testCreateEventScreenUI() {
        // Verify screen title is displayed
        composeTestRule.onNodeWithText("Create New Event").assertIsDisplayed()

        // Verify input fields are displayed
        composeTestRule.onNodeWithText("Event Name").assertIsDisplayed()
        composeTestRule.onNodeWithText("Event Date").assertIsDisplayed()
        composeTestRule.onNodeWithText("Event Location").assertIsDisplayed()
        composeTestRule.onNodeWithText("Event Description").assertIsDisplayed()
        composeTestRule.onNodeWithText("Event Category").assertIsDisplayed()
        composeTestRule.onNodeWithText("Price (Enter 'Free' or Amount)").assertIsDisplayed()

        // Enter event details
        composeTestRule.onNodeWithText("Event Name").performTextInput("Tech Meetup")
        composeTestRule.onNodeWithText("Event Date").performTextInput("2025-03-10")
        composeTestRule.onNodeWithText("Event Location").performTextInput("Nairobi, Kenya")
        composeTestRule.onNodeWithText("Event Description").performTextInput("A networking event for techies.")
        composeTestRule.onNodeWithText("Event Category").performTextInput("Networking")
        composeTestRule.onNodeWithText("Price (Enter 'Free' or Amount)").performTextInput("Free")


        composeTestRule.onNodeWithText("Save Event").assertHasClickAction()


        composeTestRule.onNodeWithText("Save Event").performClick()
    }
}
