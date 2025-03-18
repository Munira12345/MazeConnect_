package com.example.mazeconnect.eventorganizer

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mazeconnect.ui.theme.MazeConnectTheme
import io.mockk.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EventManagementTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var mockNavController: NavHostController

    @Before
    fun setUp() {
        mockNavController = mockk(relaxed = true) // Create a mock NavController

        composeTestRule.setContent {
            val fakeEvents = listOf(
                Event("1", "Tech Meetup", "2025-03-10", "Nairobi", "Tech event"),
                Event("2", "Startup Pitch", "2025-04-15", "Mombasa", "Startup event")
            )
            MazeConnectTheme {
                EventManagement(mockNavController, fakeEvents)
            }
        }
    }


    @Test
    fun testEventsAreDisplayed() {

        composeTestRule.waitForIdle() // Ensure UI updates

        // Check if events are displayed
        composeTestRule.onNodeWithText("Tech Meetup").assertIsDisplayed()
        composeTestRule.onNodeWithText("Startup Pitch").assertIsDisplayed()

        // Check if the delete button exists
       // composeTestRule.onAllNodes(hasContentDescription("Delete Event")).assertCountEquals(2)
    }
}
