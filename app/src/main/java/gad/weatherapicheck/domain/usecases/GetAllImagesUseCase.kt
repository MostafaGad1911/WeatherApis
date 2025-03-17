package gad.weatherapicheck.domain.usecases

import gad.weatherapicheck.data.repsitories.TempImageRepository
import gad.weatherapicheck.domain.entities.TempImageEntity
import kotlinx.coroutines.flow.Flow

class GetAllImagesUseCase(private val repository: TempImageRepository) {
    operator fun invoke(): Flow<List<TempImageEntity>> {
        return repository.getAllImages()
    }
}
