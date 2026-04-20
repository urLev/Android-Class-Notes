package com.goble.cameraandsensors.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goble.cameraandsensors.model.SensorRepository
import kotlinx.coroutines.launch

class SensorViewModel(context: Context): ViewModel() {
    private val sensorRepository = SensorRepository(context)

    val accelerometerData = sensorRepository.accelerometerData
    val gyroscopeData = sensorRepository.gyroscopeData
    val isListening = sensorRepository.isListening

    init {
        startListening()
    }

    fun startListening() {
        viewModelScope.launch {
            sensorRepository.startListening()
        }
    }

    fun stopListening() {
        viewModelScope.launch {
            sensorRepository.stopListening()
        }
    }

    fun onPause() {
        sensorRepository.stopListening()
    }

    override fun onCleared() {
        super.onCleared()
        sensorRepository.stopListening()
    }
}