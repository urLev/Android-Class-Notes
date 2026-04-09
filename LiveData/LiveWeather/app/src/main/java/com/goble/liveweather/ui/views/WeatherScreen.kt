package com.goble.liveweather.ui.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.goble.liveweather.data.WeatherData
import com.goble.liveweather.viewModel.WeatherUIState

@Composable
fun WeatherScreen2(
    weatherUI: WeatherUIState
) {
    val weather: WeatherData? = weatherUI.weather
    val isLoading: Boolean = weatherUI.isLoading
    val error:String? = weatherUI.onFailure

    Text(
        text = "Location: ${weather?.latitude}, ${weather?.longitude}"
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewWeather() {
    WeatherScreen2(
        weatherUI = WeatherUIState()
    )
}