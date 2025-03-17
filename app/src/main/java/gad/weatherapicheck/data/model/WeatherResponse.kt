package gad.weatherapicheck.data.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val current: Current? = null,
    val location: Location? = null
)