package com.example.mazeconnect.utils

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun getWindowSize(): WindowSizeClass {
    val context = LocalContext.current
    val activity = context as? ComponentActivity ?: return calculateWindowSizeClass(ComponentActivity())
    return calculateWindowSizeClass(activity)
}