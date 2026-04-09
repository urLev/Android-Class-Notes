package com.goble.liveweather.ui.views

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goble.liveweather.data.CurrentWeather
import com.goble.liveweather.data.DailyWeather
import com.goble.liveweather.data.WeatherData
import com.goble.liveweather.data.WeatherUnits
import com.goble.liveweather.utility.WeatherCode
import com.goble.liveweather.viewModel.WeatherUIState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    weather: WeatherData?,
    isLoading: Boolean,
    error: String?
) {
    var cityName:String by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .padding(18.dp)
            ) {
                OutlinedTextField(
                    value = cityName,
                    onValueChange = {
                        cityName = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp)
        ) {
            when {
                isLoading -> LoadingScreen()
                error != null -> ErrorScreen(error)
                weather != null -> WeatherScreen(weather)
            }
        }
    }
}


@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(error: String?) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = error ?: "Unknown Error",
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherScreen(weather: WeatherData) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            CurrentWeather(weather)
        }

        item {
            Text(
                text = "7 Day Forecast",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }

        items(weather.daily) { forecast ->
//            ForecastRow(forecast)
            ForecastCard(forecast)
        }
    }
}

@Composable
fun CurrentWeather(weather: WeatherData) {
    val current = weather.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Dickinson College",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "${current.temperature}${weather.units.tempUnit}",
            fontSize = 72.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = WeatherCode.getDescription(current.weatherCode),
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastCard(forecast: DailyWeather) {
    val dateString = forecast.date
    val date: LocalDate = LocalDate.parse(dateString)
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d")
    val formattedDate: String = date.format(formatter)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = formattedDate,
                color = MaterialTheme.colorScheme.onSurface
            )

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Daily High for $date",
                    tint = MaterialTheme.colorScheme.error
                )

                Text(
                    text = "${forecast.high.toInt()}",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.error
                )

                Spacer(modifier = Modifier.padding(horizontal = 8.dp))

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Daily Low for $date",
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "${forecast.low.toInt()}",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview()
@Composable
fun PreviewMainScreen() {
    val current = CurrentWeather(
        temperature = 43.0,
        feelsLike = 34.0,
        weatherCode = 0,
        precipitation = 0.0
    )

    val units = WeatherUnits(
        tempUnit = "°F",
        precipitationUnit = "mm"
    )

    val forecast:List<DailyWeather> = listOf(
            DailyWeather(
                date = "2026-04-09",
                high = 60.0,
                low = 33.0
            ),
            DailyWeather(
                date = "2026-04-10",
                high = 74.0,
                low = 39.0
            ),
            DailyWeather(
                date = "2026-04-11",
                high = 63.0,
                low = 46.0
            ),
            DailyWeather(
                date = "2026-04-12",
                high = 68.0,
                low = 40.0
            ),
            DailyWeather(
                date = "2026-04-13",
                high = 74.0,
                low = 53.0
            ),
            DailyWeather(
                date = "2026-04-14",
                high = 79.0,
                low = 57.0
            ),
            DailyWeather(
                date = "2026-04-15",
                high = 84.0,
                low = 60.0
            )
    )

    val weather = WeatherData(
        longitude = 0.0,
        latitude = 0.0,
        current = current,
        units = units,
        daily = forecast
    )

    val dummyData: WeatherUIState = WeatherUIState(
        weather = weather,
        onFailure = null,
        isLoading = false
    )

    MainScreen(
        weather = weather,
        error = null,
        isLoading = false
    )
}