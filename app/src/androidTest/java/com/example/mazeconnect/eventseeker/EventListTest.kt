package com.example.mazeconnect.eventseeker

import EventList
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mazeconnect.eventorganizer.Event
import com.example.mazeconnect.eventorganizer.EventManagement
import com.example.mazeconnect.ui.theme.MazeConnectTheme
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EventListTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var mockNavController: NavHostController

    @Before
    fun setUp() {
        mockNavController = mockk(relaxed = true)

        composeTestRule.setContent {
            MazeConnectTheme {
                EventList(mockNavController)
            }
        }
    }

    @Test
    fun exploreEventsTitleIsVisible() {
        composeTestRule.onNodeWithText("Explore Events").assertIsDisplayed()
    }


}
