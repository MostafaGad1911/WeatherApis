package gad.weatherapicheck.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gad.weatherapicheck.domain.entities.TempImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImageWithTemp(image: TempImageEntity)

    @Query("SELECT * FROM images")
    fun getAllImagesWithTemp(): Flow<List<TempImageEntity>>
}
