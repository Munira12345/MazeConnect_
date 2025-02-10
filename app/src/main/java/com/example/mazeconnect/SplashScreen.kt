package com.example.mazeconnect

import androidx.compose.ui.unit.dp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import com.example.mazeconnect.ui.theme.MazeConnectTheme


@Composable
fun SplashScreen(navController: NavController) {
    val colors = listOf(Color.Black, Color(0xFF6A0DAD)) // Black to purple gradient

    LaunchedEffect(Unit) {
        delay(3000) // Delay for 3 seconds
        navController.navigate("sign_up")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors))
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {

            Image(
                painter = painterResource(id = R.drawable.connect),
                contentDescription = "Connect Icon",
                modifier = Modifier.size(88.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Maze Connect",
                style = TextStyle(
                    fontSize = 28.sp,
                    fontFamily = FontFamily.Monospace,
                    fontStyle = FontStyle.Italic,
                    color = Color.White
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    MazeConnectTheme {
        val navController = rememberNavController()
        SplashScreen(navController)
    }
}
