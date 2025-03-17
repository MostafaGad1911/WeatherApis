package gad.weatherapicheck.data.datasource.local

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import gad.weatherapicheck.data.model.LocationData

class LocationUtils(context: Context) {

    private val fusedLocationProvider: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)


    @SuppressLint("MissingPermission")
    fun getLastKnownLocation(
        onSuccess: (LocationData) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        fusedLocationProvider.lastLocation.addOnCompleteListener { task: Task<android.location.Location> ->
            if (task.isSuccessful && task.result != null) {
                val location = task.result
                onSuccess(LocationData(location.latitude, location.longitude))
            } else {
                onFailure(task.exception ?: Exception("Failed to get location"))
            }
        }
    }
}
