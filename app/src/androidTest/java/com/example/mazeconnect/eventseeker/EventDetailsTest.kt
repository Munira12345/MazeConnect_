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


        composeTestRule.onNodeWithText("${mockEvent.date} • ${mockEvent.location}").assertExists().assertIsDisplayed()

        composeTestRule.onNodeWithText("Category: ${mockEvent.category}").assertExists().assertIsDisplayed()

        composeTestRule.onNodeWithText("Price: ${mockEvent.price}").assertExists().assertIsDisplayed()

        composeTestRule.onNodeWithText(mockEvent.description).assertExists().assertIsDisplayed()


        composeTestRule.onNodeWithTag("BuyTicketButton").assertExists().assertIsDisplayed()

        composeTestRule.onNodeWithTag("RSVPButton").assertExists().assertIsNotEnabled()

         composeTestRule.onNodeWithText("Back to Events").assertExists().assertIsDisplayed()
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


        composeTestRule.onNodeWithTag("BackToEventsButton").performClick()

    }
}
