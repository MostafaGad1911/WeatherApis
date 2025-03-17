package gad.weatherapicheck.data.datasource.remote

import gad.weatherapicheck.data.model.WeatherResponse
import gad.weatherapicheck.utils.Constants.WEATHER_API_KEY
import gad.weatherapicheck.utils.Constants.WEATHER_BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class KtorApiServices(private val client: HttpClient) {

    suspend fun getWeather(lat: Double, lng: Double): WeatherResponse =
        client.get("$WEATHER_BASE_URL?q=$lat,$lng&lang=ar&key=$WEATHER_API_KEY").body()


}