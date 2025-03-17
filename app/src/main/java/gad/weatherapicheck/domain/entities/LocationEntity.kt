package gad.weatherapicheck.domain.entities

import gad.weatherapicheck.data.model.LocationData

data class LocationEntity(
    val latitude: Double?,
    val longitude: Double?
){
    fun toLocationData() = LocationData(
        latitude = latitude!!,
        longitude = longitude!!
    )

    fun locationString() = "Latitude: $latitude, Longitude: $longitude"
}
