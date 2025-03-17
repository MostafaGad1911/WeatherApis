package gad.weatherapicheck.domain.usecases

import gad.weatherapicheck.data.repsitories.LocationRepository
import gad.weatherapicheck.domain.entities.LocationEntity

class GetUserLocationUseCase(private val repository: LocationRepository) {
    suspend operator fun invoke(): LocationEntity {
        return repository.getUserLocation()
    }
}
