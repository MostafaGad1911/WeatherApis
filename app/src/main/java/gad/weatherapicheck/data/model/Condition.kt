package gad.weatherapicheck.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Condition(
    val code: Int? = null,
    val icon: String? = null,
    val text: String? = null
)