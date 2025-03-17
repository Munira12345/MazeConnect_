package com.example.mazeconnect.eventseeker



import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import org.junit.Rule
import org.junit.Test

class EventSeekerHomePageTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testEventSeekerHomePage_uiElementsDisplayed() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            EventSeekerHomePage(navController)
        }

        // ✅ Verify the search field exists
        composeTestRule.onNodeWithTag("SearchField").assertExists().assertIsDisplayed()

        // ✅ Verify the "Browse Events" title is displayed
        composeTestRule.onNodeWithTag("BrowseEventsTitle").assertExists().assertIsDisplayed()

        // ✅ Verify category buttons are displayed
        composeTestRule.onNodeWithTag("ConcertButton").assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag("AwardButton").assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag("MusicButton").assertExists().assertIsDisplayed()

        // ✅ Scroll and check event items (assuming at least one event is loaded)
        composeTestRule.onNodeWithTag("EventCard_1234", useUnmergedTree = true).assertExists()
    }

    @Test
    fun testClickEventCard_navigatesToDetails() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            EventSeekerHomePage(navController)
        }

        // ✅ Click on an event card and verify navigation works
        composeTestRule.onNodeWithTag("EventCard_1234", useUnmergedTree = true)
            .performClick()

        // ✅ Check if navigation to event details happened
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("EventDetailsScreen").assertExists()
    }
}
