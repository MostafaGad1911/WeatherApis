package gad.weatherapicheck.data.repsitories

import gad.weatherapicheck.data.model.WeatherResponse

interface WeatherRepository {
    suspend fun getWeather(lat: Double, lng: Double): WeatherResponse
}