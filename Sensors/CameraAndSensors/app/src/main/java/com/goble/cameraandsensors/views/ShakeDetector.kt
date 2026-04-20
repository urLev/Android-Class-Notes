package com.goble.cameraandsensors.views

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ShakeDetector(
    shakeDetected: Boolean,
    shakeIntensity: Float,
    onReset: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (shakeDetected) Color(0xFF5F2C2C) else Color(0xFF2C2C2C),
        label = "shake_bg_color"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, shape = RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Shake Detection",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (shakeDetected) Color(0xFFFF6B6B) else Color(0xFF999999)
            )

            if (shakeDetected) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "🎯 Shake Detected!",
                        color = Color(0xFFFF6B6B),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Intensity: ${String.format(locale = java.util.Locale.US, 
                            "%.1f",
                            shakeIntensity)} m/s²",
                        color = Color.White,
                        fontSize = 14.sp
                    )

                    Button(
                        onClick = onReset,
                        modifier = Modifier.align(Alignment.End),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4A2C2C)
                        )
                    ) {
                        Text("Reset", fontSize = 12.sp)
                    }
                }
            } else {
                Text(
                    text = "Device is steady",
                    color = Color(0xFF999999),
                    fontSize = 14.sp
                )
            }
        }
    }
}