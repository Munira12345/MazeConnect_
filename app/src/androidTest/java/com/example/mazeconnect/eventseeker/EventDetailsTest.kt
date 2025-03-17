package com.example.mazeconnect.eventseeker


import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.mazeconnect.EventData
import org.junit.Rule
import org.junit.Test

class EventDetailsTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testEventDetails_uiElementsDisplayed() {
        val mockEvent = EventData(
            id = "123",
            name = "Worship Night",
            description = "A night of worship and praise.",
            date = "December 30, 2025",
            location = "Nairobi",
            category = "Religious",
            price = "500 KES"
        )

        composeTestRule.setContent {
            val navController = rememberNavController()
            EventDetails(navController, mockEvent = mockEvent)
        }

        // ✅ Verify Event Name
        composeTestRule.onNodeWithTag("EventName").assertExists().assertIsDisplayed()

        // ✅ Verify Event Date and Location
        composeTestRule.onNodeWithTag("EventDateLocation").assertExists().assertIsDisplayed()

        // ✅ Verify Category
        composeTestRule.onNodeWithTag("EventCategory").assertExists().assertIsDisplayed()

        // ✅ Verify Price
        composeTestRule.onNodeWithTag("EventPrice").assertExists().assertIsDisplayed()

        // ✅ Verify Description
        composeTestRule.onNodeWithTag("EventDescription").assertExists().assertIsDisplayed()

        // ✅ Verify Buy Ticket Button (Since price is not Free)
        composeTestRule.onNodeWithTag("BuyTicketButton").assertExists().assertIsDisplayed()

        // ✅ Verify RSVP Button is Disabled (Since it’s a paid event)
        composeTestRule.onNodeWithTag("RSVPButton").assertExists().assertIsNotEnabled()

        // ✅ Verify Back Button
        composeTestRule.onNodeWithTag("BackToEventsButton").assertExists().assertIsDisplayed()
    }

    @Test
    fun testClickBackButton_navigatesBackToEvents() {
        val mockEvent = EventData(
            id = "123",
            name = "Worship Night",
            description = "A night of worship and praise.",
            date = "December 30, 2025",
            location = "Nairobi",
            category = "Religious",
            price = "500 KES"
        )

        composeTestRule.setContent {
            val navController = rememberNavController()
            EventDetails(navController, mockEvent = mockEvent)
        }

        // ✅ Click Back Button
        composeTestRule.onNodeWithTag("BackToEventsButton").performClick()

        // ✅ Wait for UI state to update
        composeTestRule.waitForIdle()

        // ✅ Verify navigation happened (assuming 'event_list' screen has a tag)
        composeTestRule.onNodeWithTag("EventListScreen").assertExists()
    }
}
