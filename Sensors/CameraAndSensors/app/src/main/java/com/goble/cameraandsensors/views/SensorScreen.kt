package com.goble.cameraandsensors.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goble.cameraandsensors.ViewModel.SensorViewModel

@Composable
fun SensorScreen(viewModel: SensorViewModel) {
    val accelerometerData by viewModel.accelerometerData.collectAsState()
    val gyroscopeData by viewModel.gyroscopeData.collectAsState()
    val isListening by viewModel.isListening.collectAsState()

    // Shake detection
    var shakeDetected by rememberSaveable { mutableStateOf(false) }
    var shakeIntensity by rememberSaveable { mutableFloatStateOf(0f) }

    LaunchedEffect(accelerometerData) {
        if (accelerometerData.magnitude > 25f) {
            shakeDetected = true
            shakeIntensity = accelerometerData.magnitude
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFF1A1A1A))
            .padding(top = 58.dp)
//            .padding(bottom = 8.dp)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Motion Sensor Dashboard",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF00D9FF),
            fontFamily = FontFamily.Monospace
        )

//        Spacer(modifier = Modifier.height(8.dp))

        // Status Indicator
        StatusIndicator(isListening = isListening)

        // Accelerometer Card
        SensorCard(
            title = "Accelerometer (m/s²)",
            sensorData = accelerometerData,
            backgroundColor = Color(0xFF1E3A5F)
        )

        // Gyroscope Card
        SensorCard(
            title = "Gyroscope (rad/s)",
            sensorData = gyroscopeData,
            backgroundColor = Color(0xFF2D1B3D)
        )

        // Shake Detector
        ShakeDetector(
            shakeDetected = shakeDetected,
            shakeIntensity = shakeIntensity,
            onReset = { shakeDetected = false }
        )

        // Visualization
        MotionVisualizer(
            accelerometerData = accelerometerData,
            gyroscopeData = gyroscopeData
        )

        // Control Button
        Button(
            onClick = {
                if (isListening) {
                    viewModel.stopListening()
                } else {
                    viewModel.startListening()
                }
            },
            modifier = Modifier
                .width(200.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isListening) Color(0xFFFF6B6B) else Color(0xFF51CF66)
            )
        ) {
            Text(
                text = if (isListening) "Stop Listening" else "Start Listening",
                fontWeight = FontWeight.Bold
            )
        }
    }
}