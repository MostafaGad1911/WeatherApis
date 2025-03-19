package gad.weatherapicheck.domain.usecases

import android.content.Context
import gad.weatherapicheck.data.repsitories.LocationRepository
import gad.weatherapicheck.domain.entities.LocationEntity

class GetUserLocationUseCase(private val repository: LocationRepository) {
    suspend operator fun invoke(context: Context): LocationEntity {
        return repository.getUserLocation(context = context)
    }
}
