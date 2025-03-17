package com.example.mazeconnect.eventorganizer

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.mazeconnect.ui.theme.MazeConnectTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EventManagementTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MazeConnectTheme {
                val navController = rememberNavController()
                EventManagement(navController)
            }
        }
    }

    @Test
    fun testEventManagementScreenUI() {
        // Verify screen title
        composeTestRule.onNodeWithText("Event Management").assertIsDisplayed()

        // Check if the loading indicator appears
        composeTestRule.onNode(hasTestTag("loading_indicator")).assertExists()

        // Simulate loading completion
        composeTestRule.waitForIdle()

        // Verify message when no events exist
        composeTestRule.onNodeWithText("No events. Click to create event.").assertIsDisplayed()

        // Click the message to navigate to create event
        composeTestRule.onNodeWithText("No events. Click to create event.").performClick()
    }

    @Test
    fun testEventsAreDisplayed() {
        val fakeEvents = listOf(
            Event(id = "1", name = "Tech Meetup", date = "2025-03-10", location = "Nairobi", description = "Tech event"),
            Event(id = "2", name = "Startup Pitch", date = "2025-04-15", location = "Mombasa", description = "Startup event")
        )

        composeTestRule.waitForIdle() // Ensure UI updates

        // Check if events are displayed
        composeTestRule.onNodeWithText("Tech Meetup").assertIsDisplayed()
        composeTestRule.onNodeWithText("Startup Pitch").assertIsDisplayed()

        // Check if the delete button exists
        composeTestRule.onAllNodes(hasContentDescription("Delete Event")).assertCountEquals(2)
    }
}
