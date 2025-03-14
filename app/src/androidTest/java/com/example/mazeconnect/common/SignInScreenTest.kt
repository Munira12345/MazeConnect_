package com.example.mazeconnect.common


import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.testing.TestNavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import com.example.mazeconnect.ui.theme.MazeConnectTheme
import org.junit.Rule
import org.junit.Test

class SignInScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testSignInScreenComponentsDisplayed() {
        composeTestRule.setContent {
            MazeConnectTheme {
                val navController = rememberNavController()
                SignInScreen(navController)
            }
        }

        // Check if Email field is displayed
        composeTestRule.onNode(hasText("Email")).assertIsDisplayed()

        // Check if Password field is displayed
        composeTestRule.onNode(hasText("Password")).assertIsDisplayed()

        // Check if Sign In button is displayed
        composeTestRule.onNode(hasText("Sign In")).assertIsDisplayed()

        // Check if "Forgot Password?" text is displayed
        composeTestRule.onNode(hasText("Forgot Password?")).assertIsDisplayed()
    }

    @Test
    fun testEnteringEmailAndPassword() {
        composeTestRule.setContent {
            MazeConnectTheme {
                val navController = rememberNavController()
                SignInScreen(navController)
            }
        }

        // Enter text into Email field
        composeTestRule.onNode(hasText("Email")).performTextInput("test@example.com")

        // Enter text into Password field
        composeTestRule.onNode(hasText("Password")).performTextInput("password123")

        // Assert the button is enabled
        composeTestRule.onNode(hasText("Sign In")).assertIsEnabled()
    }

    @Test
    fun testSignInButtonDisabledWhenFieldsAreEmpty() {
        composeTestRule.setContent {
            MazeConnectTheme {
                val navController = rememberNavController()
                SignInScreen(navController)
            }
        }

        // Assert that Sign In button is disabled initially
        composeTestRule.onNode(hasText("Sign In")).assertIsNotEnabled()
    }

    @Test
    fun testSignUpNavigation() {
        lateinit var navController: TestNavHostController

        composeTestRule.setContent {
            MazeConnectTheme {
                navController = TestNavHostController(ApplicationProvider.getApplicationContext()).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                SignInScreen(navController)
            }
        }

        // Click the "Sign Up" button
        composeTestRule.onNode(hasText("Sign Up")).performClick()

        // Verify if the navigation destination changed
        assert(navController.currentDestination?.route == "sign_up") {
            "Expected navigation to 'sign_up', but was ${navController.currentDestination?.route}"
        }
    }
}
