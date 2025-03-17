package gad.weatherapicheck.domain.usecases

import gad.weatherapicheck.data.model.WeatherResponse
import gad.weatherapicheck.data.repsitories.WeatherRepository

class GetWeatherUseCase(private val repository: WeatherRepository) {
    suspend operator fun invoke(lat: Double, lng: Double): WeatherResponse {
        return repository.getWeather(lat, lng)
    }
}
