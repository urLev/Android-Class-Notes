package com.goble.cameraandsensors.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goble.cameraandsensors.model.SensorData

@Composable
fun MotionVisualizer(
    accelerometerData: SensorData,
    gyroscopeData: SensorData
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C3E50))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Real-Time Visualization",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00D9FF)
            )

            // Accelerometer Magnitude Bar
            ProgressBar(
                label = "Accel Magnitude",
                value = accelerometerData.magnitude,
                maxValue = 50f,
                color = Color(0xFF3498DB)
            )

            // Gyroscope Magnitude Bar
            ProgressBar(
                label = "Gyro Magnitude",
                value = gyroscopeData.magnitude,
                maxValue = 5f,
                color = Color(0xFFE74C3C)
            )
        }
    }
}

@Composable
fun ProgressBar(
    label: String,
    value: Float,
    maxValue: Float,
    color: Color
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(
                    Color(0xFF555555),
                    shape = RoundedCornerShape(4.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth((value / maxValue).coerceIn(0f, 1f))
                    .background(color, shape = RoundedCornerShape(4.dp))
            )
        }
    }
}