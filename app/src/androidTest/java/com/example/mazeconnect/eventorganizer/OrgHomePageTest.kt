package com.example.mazeconnect.eventorganizer


import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.mazeconnect.ui.theme.MazeConnectTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class OrgHomePageTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MazeConnectTheme {
                val navController = rememberNavController()
                OrgHomePage(navController)
            }
        }
    }

    @Test
    fun testProfilePictureIsDisplayed() {
        composeTestRule.onNodeWithContentDescription("User Profile").assertIsDisplayed()
    }

    @Test
    fun testSubscribersTextDisplayed() {
        composeTestRule.onNodeWithText("Subscribers").assertIsDisplayed()
    }

    @Test
    fun testLazyRowEventsDisplay() {
        // If events exist, check the first event is displayed
        composeTestRule.waitForIdle()
        val eventNode = composeTestRule.onAllNodesWithTag("EventCard").onFirst()
        eventNode.assertExists()
    }

    @Test
    fun testCreateNewEventButtonClickable() {
        composeTestRule.onNodeWithText("Create New Event").assertIsDisplayed().assertHasClickAction().performClick()
    }

    @Test
    fun testManageEventsButtonClickable() {
        composeTestRule.onNodeWithText("Manage Events").assertIsDisplayed().assertHasClickAction().performClick()
    }
}
