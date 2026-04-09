package com.goble.liveweather

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.goble.liveweather.ui.theme.LiveWeatherTheme
import com.goble.liveweather.ui.views.MainScreen
import com.goble.liveweather.viewModel.WeatherViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val darkColorScheme = darkColorScheme(
                primary = Color(0xFF3DDC84),
                onPrimary = Color(0xFF1a1a2e),
                background = Color(0xFF1a1a2e),
                onBackground = Color(0xFFeaeaea),
                surface = Color(0xFF16213e),
                onSurface = Color(0xFFeaeaea),
                onSurfaceVariant = Color(0xFFa0a0a0),
                error = Color(0xFFFF6B6B)
            )

            MaterialTheme(
                colorScheme = darkColorScheme
            ) {
                val viewModel: WeatherViewModel = viewModel()
                val uiState = viewModel.uiState.collectAsState()

                MainScreen(
                    weather = uiState.value.weather,
                    isLoading = uiState.value.isLoading,
                    error = uiState.value.onFailure
                )
            }
        }
    }
}