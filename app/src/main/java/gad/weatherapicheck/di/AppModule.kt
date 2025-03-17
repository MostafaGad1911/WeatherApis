package gad.weatherapicheck.di


import androidx.room.Room
import gad.weatherapicheck.data.datasource.local.ImageDatabase
import gad.weatherapicheck.data.datasource.local.LocationUtils
import gad.weatherapicheck.data.datasource.remote.KtorApiServices
import gad.weatherapicheck.data.datasource.remote.WeatherDataSource
import gad.weatherapicheck.data.datasource.remote.provideHttpClient
import gad.weatherapicheck.data.repsitories.LocationRepository
import gad.weatherapicheck.data.repsitories.LocationRepositoryImpl
import gad.weatherapicheck.data.repsitories.TempImageRepository
import gad.weatherapicheck.data.repsitories.WeatherRepository
import gad.weatherapicheck.data.repsitories.WeatherRepositoryImpl
import gad.weatherapicheck.domain.usecases.GetAllImagesUseCase
import gad.weatherapicheck.domain.usecases.GetUserLocationUseCase
import gad.weatherapicheck.domain.usecases.GetWeatherUseCase
import gad.weatherapicheck.domain.usecases.InsertImageUseCase
import gad.weatherapicheck.presentation.viewmodel.ImageViewModel
import gad.weatherapicheck.presentation.viewmodel.LocationViewModel
import gad.weatherapicheck.presentation.viewmodel.WeatherViewModel
import io.ktor.client.HttpClient
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Ktor client
    single { provideHttpClient() }

    // Provide data sources
    single { LocationUtils(get()) }
    single { WeatherDataSource(get()) }

    // Room DB
    single {
        Room.databaseBuilder(
            get(),
            ImageDatabase::class.java,
            "temp_task_image_database"
        ).fallbackToDestructiveMigration().build()
    }

    single { get<ImageDatabase>().imageDao() }

    // Provide Repositories
    single<LocationRepository> { LocationRepositoryImpl(get()) }
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
    single { TempImageRepository(get()) }

    // Provide Use Cases
    factory { GetUserLocationUseCase(get()) }
    factory { GetWeatherUseCase(get()) }
    factory { InsertImageUseCase(get()) }
    factory { GetAllImagesUseCase(get()) }

    // Provide ViewModel
    viewModel { LocationViewModel(application = androidApplication(), get()) }
    viewModel { WeatherViewModel(application = androidApplication(), get()) }
    viewModel { ImageViewModel(application = androidApplication() ,get(), get()) }

    // Provide KtorApiServices
    single { KtorApiServices(get()) }
}
