package com.example.mazeconnect.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ReusableOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    borderColor: Color = Color(0xFF800080), // Purple border
    textColor: Color = Color(0xFF800080) // Purple text
) {
    OutlinedButton(
        onClick = { if (!isLoading) onClick() },
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        border = BorderStroke(2.dp, borderColor),
        enabled = !isLoading
    ) {
        Text(if (isLoading) "Saving..." else text, color = textColor)
    }
}
