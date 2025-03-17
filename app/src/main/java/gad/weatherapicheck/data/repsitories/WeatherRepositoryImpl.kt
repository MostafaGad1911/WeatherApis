package gad.weatherapicheck.data.repsitories

import gad.weatherapicheck.data.datasource.remote.WeatherDataSource
import gad.weatherapicheck.data.model.WeatherResponse

class WeatherRepositoryImpl(private val dataSource: WeatherDataSource) : WeatherRepository {
    override suspend fun getWeather(lat: Double, lng: Double): WeatherResponse {
        return dataSource.getWeather(lat, lng)
    }
}


