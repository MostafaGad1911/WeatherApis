package gad.weatherapicheck.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gad.weatherapicheck.data.model.WeatherResponse
import gad.weatherapicheck.domain.usecases.GetWeatherUseCase
import gad.weatherapicheck.presentation.core.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application, private val getWeatherUseCase: GetWeatherUseCase) :
    BaseViewModel(
        application = application
    ) {

    private val _weatherState = MutableStateFlow<WeatherResponse?>(null)
    val weatherState: StateFlow<WeatherResponse?> = _weatherState

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchWeather(lat: Double, lng: Double) {
        viewModelScope.launch {
            try {
                val weather = getWeatherUseCase.invoke(lat, lng)
                _weatherState.value = weather
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Unknown Error"
            }
        }
    }

    fun resetErrorMessage() {
        _errorMessage.value = null
    }
}
