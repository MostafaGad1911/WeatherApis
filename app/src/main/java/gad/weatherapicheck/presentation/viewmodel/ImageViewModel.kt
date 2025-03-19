package gad.weatherapicheck.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gad.weatherapicheck.domain.entities.TempImageEntity
import gad.weatherapicheck.domain.usecases.GetAllImagesUseCase
import gad.weatherapicheck.domain.usecases.InsertImageUseCase
import gad.weatherapicheck.presentation.core.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ImageViewModel(
    application: Application,
    private val insertImageUseCase: InsertImageUseCase,
    private val getAllImagesUseCase: GetAllImagesUseCase
) : BaseViewModel(application = application) {

    private val _weatherHistory = MutableStateFlow<List<TempImageEntity>>(emptyList())
    val weatherHistory: StateFlow<List<TempImageEntity>> = _weatherHistory.asStateFlow()

    private val _imageUri = MutableStateFlow<String?>(null)
    val imageUri: StateFlow<String?> = _imageUri.asStateFlow()

    fun fetchImages() {
        viewModelScope.launch {
            getAllImagesUseCase().collect { _weatherHistory1 ->
                _weatherHistory.value = _weatherHistory1
                Log.i("ImageViewModel", "fetchImages: ${_weatherHistory1.size}")
            }
        }
    }

    fun insertImage(tempImageEntity: TempImageEntity) {
        viewModelScope.launch {
            _imageUri.value = tempImageEntity.uri
            insertImageUseCase(tempImageEntity)
            fetchImages()
        }
    }

    fun resetImages() {
        _weatherHistory.value = emptyList()
    }
}
