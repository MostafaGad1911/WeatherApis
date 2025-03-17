package gad.weatherapicheck.data.datasource.remote

import io.ktor.client.HttpClient
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun provideHttpClient(): HttpClient {
    return HttpClient {
        install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }
}
