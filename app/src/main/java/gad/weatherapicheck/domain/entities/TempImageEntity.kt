package gad.weatherapicheck.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class TempImageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val uri: String,
    val temp: Double,
    val wind: Double,
    val humidity: Double,
    val pressure: Double,
    val weatherStateImage: String = ""
)
