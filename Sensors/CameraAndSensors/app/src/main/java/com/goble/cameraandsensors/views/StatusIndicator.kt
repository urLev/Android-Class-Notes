package com.goble.cameraandsensors.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatusIndicator(isListening: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (isListening) Color(0xFF2D5016) else Color(0xFF4A2C2C),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Sensor Status",
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = if (isListening) "🟢 Active" else "🔴 Inactive",
            color = if (isListening) Color(0xFF51CF66) else Color(0xFFFF6B6B),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}