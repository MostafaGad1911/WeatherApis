package gad.weatherapicheck.data.repsitories

import android.content.Context
import gad.weatherapicheck.domain.entities.LocationEntity

interface LocationRepository {
    suspend fun getUserLocation(context: Context): LocationEntity
}
