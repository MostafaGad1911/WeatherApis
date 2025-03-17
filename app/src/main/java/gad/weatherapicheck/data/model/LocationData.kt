package gad.weatherapicheck.data.model

data class LocationData(
    val latitude: Double,
    val longitude: Double
){
    fun locationString() = "Latitude: $latitude, Longitude: $longitude"
}