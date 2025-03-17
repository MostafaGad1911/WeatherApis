package gad.weatherapicheck.data.repsitories

import gad.weatherapicheck.domain.entities.LocationEntity

interface LocationRepository {
    suspend fun getUserLocation(): LocationEntity
}
