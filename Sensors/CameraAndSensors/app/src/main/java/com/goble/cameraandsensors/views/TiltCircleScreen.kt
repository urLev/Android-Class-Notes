package com.goble.cameraandsensors.views

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.goble.cameraandsensors.model.TiltSensorListener

@Composable
fun TiltCircleScreen() {
    // this should be placed in the repository, but for sketching purposes I just built this here
    val context = LocalContext.current
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    // We are first going to get the raw tilt values and then add a smoothing factor to
    // prevent the jitteriness of the ball movement
    var rawTiltX by remember { mutableFloatStateOf(0f) }
    var smoothedTiltX by remember { mutableFloatStateOf(0f) }

    var rawTiltY by remember { mutableFloatStateOf(0f) }
    var smoothedTiltY by remember { mutableFloatStateOf(0f) }

    // if this is true we'll stop listening
    var isLocked by remember { mutableStateOf(false) }

    val listener = remember {
        TiltSensorListener { tilt ->
            if (!isLocked) {
                // the value is negated to when we tilt the phone right, it moves right and vice versa
                rawTiltX = -tilt.x
                // this format will slowly change the
                smoothedTiltX = smoothedTiltX * 0.9f + rawTiltX * 0.1f

                // same as above
                rawTiltY = -tilt.y
                smoothedTiltY = smoothedTiltY * 0.8f + rawTiltY + 0.2f
            }
        }
    }

    // again, normally done in the repository, but for sketching purposes I'll leave this here
    DisposableEffect(Unit) {
        sensorManager.registerListener(
            listener,
            accelerometer,
            SensorManager.SENSOR_DELAY_GAME
        )

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    // Now let's start animating the ball
    // even with the smoothing applied, the ball can still feel jumpy
    // animating the ball will remove those jumps
    val animatedTiltX by animateFloatAsState(
        targetValue = smoothedTiltX,
//        animationSpec = tween(durationMillis = 50)
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    val animatedTiltY by animateFloatAsState(
        targetValue = smoothedTiltY,
        // spring can provide a more natural feel to the animation
        // using this can allow us to side step building a full
        // physics engine
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Canvas(
            modifier = Modifier.weight(1f)
                .fillMaxWidth()
        ) {
            val radius = 150f
            val screenCenterX = size.width / 2
            val screenCenterY = size.height / 2

            val targetTiltX = if (isLocked) animatedTiltX else smoothedTiltX
            val targetTiltY = if (isLocked) animatedTiltY else smoothedTiltY

            // the targetedTilt is going to return a small value (~-10 - 10)
            // so multiplying it by some value, like 50, allows for larger movements
            val scaledX = screenCenterX + (targetTiltX * 50)
            val scaledY = screenCenterY + (targetTiltY * 50)

            // instead of clamping the movement to stay within bounds, let's just compress movement
            // when we get close to the edge. Consider this a "soft boundary"
            val resistedX = applyEdgeResistance(scaledX, radius, size.width - radius)
            val resistedY = applyEdgeResistance(scaledY, radius, size.height - radius)

            // now that the movement slowest down in growth when we are approaching the edge
            // let's just clamp the values to we remain within our desired values
            val finalX = resistedX.coerceIn(radius, size.width - radius)
            val finalY = resistedY.coerceIn(radius, size.height - radius)


            drawCircle(
                color = Color.Cyan,
                radius = radius,
                center = Offset(finalX, finalY)
            )
        }

        Button(
            onClick = { isLocked = !isLocked },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isLocked) "Unlock" else "Lock")
        }
    }
}

fun applyEdgeResistance(
    x:Float, min: Float, max: Float
): Float {
    val buffer = 250f

    return when {
        x < min + buffer -> {
            val t = (x - min) / buffer
            min + buffer * t * t
        }
        x > max - buffer -> {
            val t = (max - x) / buffer
            max - buffer * t * t
        }
        else -> x
    }
}