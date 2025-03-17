package com.example.mazeconnect.common

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.mazeconnect.ui.theme.MazeConnectTheme
import org.junit.Rule
import org.junit.Test

class SignUpScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testSignUpScreenComponentsDisplayed() {
        composeTestRule.setContent {
            MazeConnectTheme {
                val navController = rememberNavController()
                SignUpScreen(navController)
            }
        }

        // Verify if Name, Email, Password fields are displayed
        composeTestRule.onNode(hasText("Name")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Email")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Password")).assertIsDisplayed()

        // Check if Sign Up button is displayed
        composeTestRule.onNode(hasText("Sign Up")).assertIsDisplayed()

        // Check if "Already have an account?" text is displayed
        composeTestRule.onNode(hasText("Already have an account?")).assertIsDisplayed()

        // Check if Sign In button is displayed
        composeTestRule.onNode(hasText("Sign In")).assertIsDisplayed()
    }

    @Test
    fun testEnteringDetailsEnablesSignUpButton() {
        composeTestRule.setContent {
            MazeConnectTheme {
                val navController = rememberNavController()
                SignUpScreen(navController)
            }
        }

        // Enter text into Name field
        composeTestRule.onNode(hasText("Name")).performTextInput("John Doe")

        // Enter text into Email field
        composeTestRule.onNode(hasText("Email")).performTextInput("johndoe@example.com")

        // Enter text into Password field
        composeTestRule.onNode(hasText("Password")).performTextInput("password123")

        // Assert the Sign Up button is enabled
        composeTestRule.onNode(hasText("Sign Up")).assertIsEnabled()
    }

    @Test
    fun testSignUpButtonDisabledWhenFieldsAreEmpty() {
        composeTestRule.setContent {
            MazeConnectTheme {
                val navController = rememberNavController()
                SignUpScreen(navController)
            }
        }

        // Assert Sign Up button is initially disabled
        composeTestRule.onNode(hasText("Sign Up")).assertIsNotEnabled()
    }

    @Test
    fun testSignInNavigation() {
        composeTestRule.setContent {
            MazeConnectTheme {
                val navController = rememberNavController()
                SignUpScreen(navController)
            }
        }

        // Click the "Sign In" button
        composeTestRule.onNode(hasText("Sign In")).performClick()

        // Ideally, check if navigation was triggered
        // but rememberNavController() doesn’t allow direct assertion
        // If using TestNavHostController, you can verify navigation
    }
}
