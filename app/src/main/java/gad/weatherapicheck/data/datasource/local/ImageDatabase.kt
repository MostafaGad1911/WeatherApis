package gad.weatherapicheck.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import gad.weatherapicheck.domain.entities.TempImageEntity

@Database(entities = [TempImageEntity::class], version = 3, exportSchema = false)
abstract class ImageDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao

    companion object {
        @Volatile
        private var INSTANCE: ImageDatabase? = null

        fun getDatabase(context: Context): ImageDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ImageDatabase::class.java,
                    "temp_task_image_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
