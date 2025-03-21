package gad.weatherapicheck.data.repsitories

import android.content.Context
import gad.weatherapicheck.data.datasource.local.LocationUtils
import gad.weatherapicheck.domain.entities.LocationEntity
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class LocationRepositoryImpl(private val locationUtils: LocationUtils) : LocationRepository {

    override suspend fun getUserLocation(context: Context): LocationEntity {
        return suspendCancellableCoroutine { continuation ->
            locationUtils.getLastKnownLocation(context = context,
                onSuccess = { locationData ->
                    continuation.resume(
                        LocationEntity(
                            latitude = locationData.latitude,
                            longitude = locationData.longitude
                        )
                    )
                },
                onFailure = { exception ->
                    continuation.resumeWithException(exception)
                }
            )
        }
    }
}

