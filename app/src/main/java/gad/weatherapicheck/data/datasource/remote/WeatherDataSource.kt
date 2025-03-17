package gad.weatherapicheck.data.datasource.remote

import gad.weatherapicheck.data.model.WeatherResponse
import gad.weatherapicheck.utils.Constants.WEATHER_API_KEY
import gad.weatherapicheck.utils.Constants.WEATHER_BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class WeatherDataSource(private val ktorApiServices: KtorApiServices) {

    suspend fun getWeather(lat: Double, lng: Double): WeatherResponse = ktorApiServices.getWeather(lat, lng)
}
