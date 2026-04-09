package com.goble.liveweather.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.goble.liveweather.data.WeatherData
import com.goble.liveweather.network.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class WeatherUIState(
    val isLoading: Boolean = false,
    val weather: WeatherData? = null,
    val onFailure:String? = null
)

class WeatherViewModel: ViewModel() {
    private val dickinsonLong = 40.2027
    private val dickinsonLat = -77.2008

    private val repo = WeatherRepository()
    private val _uiState = MutableStateFlow(WeatherUIState())
    val uiState: StateFlow<WeatherUIState> = _uiState

    private val _isSearching = MutableStateFlow(false)
    val isSearching:StateFlow<Boolean> = _isSearching

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    init {
        fetchWeather()
    }

    fun fetchWeather(
        longitude:Double = dickinsonLong,
        latitude:Double = dickinsonLat
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, onFailure = null)
            val result = repo.getCurrentWeather(longitude = longitude, latitude = latitude)

            result.onSuccess { data ->
                _uiState.value = _uiState.value.copy(
                    weather = data,
                    isLoading = false
                )
            }.onFailure { exception ->
                _uiState.value=  _uiState.value.copy(
                    onFailure = exception.message,
                    isLoading = false
                )
            }
        }
    }

    fun onSearchTextChange(text:String) {
        _query.value = text
    }
}