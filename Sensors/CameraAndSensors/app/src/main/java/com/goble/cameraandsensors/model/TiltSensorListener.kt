package com.goble.cameraandsensors.model

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener

data class Tilt(
    val x: Float = 0f,
    val y: Float = 0f
)
class TiltSensorListener(
    private val onTiltChanged: (Tilt) -> Unit
): SensorEventListener {
    override fun onSensorChanged(event: SensorEvent) {
        val tiltX = event.values[0]
        val tiltY = event.values[1]
        val tilt = Tilt(x = tiltX, y = tiltY)

        onTiltChanged(tilt)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }
}