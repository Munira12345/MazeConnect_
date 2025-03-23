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
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("search").assertExists().assertIsDisplayed()


        composeTestRule.onNodeWithTag("BrowseEventsTitle").assertExists().assertIsDisplayed()


        composeTestRule.onNodeWithTag("buttons").assertExists().assertIsDisplayed()



    }

}
