package gad.weatherapicheck.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gad.weatherapicheck.data.model.LocationData
import gad.weatherapicheck.domain.usecases.GetUserLocationUseCase
import gad.weatherapicheck.presentation.core.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationViewModel(
    application: Application,
    private var getUserLocationUseCase: GetUserLocationUseCase ,
) : BaseViewModel(application = application) {
    private val _locationState = MutableStateFlow<LocationData?>(null)
    val locationState: StateFlow<LocationData?> = _locationState


    private val _errorState = MutableStateFlow<String?>(null)

    private val _launchCamera = MutableStateFlow(false)
    val launchCamera: StateFlow<Boolean> = _launchCamera


    fun fetchLocation() {
        viewModelScope.launch {
            try {
                val location = getUserLocationUseCase.invoke(context = application)
                _locationState.value = location.toLocationData()
            }catch (e: Exception) {
                e.printStackTrace()
                _errorState.value = e.message
            }
        }
    }

    fun launchCamera() {
        _launchCamera.value = true
    }

    fun resetLaunchCamera() {
        _launchCamera.value = false
    }


}

