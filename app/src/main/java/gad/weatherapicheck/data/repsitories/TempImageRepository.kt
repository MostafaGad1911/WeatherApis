package gad.weatherapicheck.data.repsitories

import gad.weatherapicheck.data.datasource.local.ImageDao
import gad.weatherapicheck.domain.entities.TempImageEntity
import kotlinx.coroutines.flow.Flow

class TempImageRepository(private val dao: ImageDao) {


    fun getAllImages(): Flow<List<TempImageEntity>> = dao.getAllImagesWithTemp()

    suspend fun insertImage(image: TempImageEntity) {
        dao.insertImageWithTemp(image)
    }
}
