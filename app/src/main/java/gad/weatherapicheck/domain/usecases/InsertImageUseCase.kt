package gad.weatherapicheck.domain.usecases

import gad.weatherapicheck.data.repsitories.TempImageRepository
import gad.weatherapicheck.domain.entities.TempImageEntity

class InsertImageUseCase(private val repository: TempImageRepository) {
    suspend operator fun invoke(image: TempImageEntity) {
        repository.insertImage(image)
    }
}

