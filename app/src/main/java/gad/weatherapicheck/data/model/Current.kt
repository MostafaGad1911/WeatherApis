package gad.weatherapicheck.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Current(
    val cloud: Int? =null,
    val dewpoint_c: Double? =null,
    val dewpoint_f: Double? =null,
    val feelslike_c: Double? =null,
    val feelslike_f: Double? =null,
    val gust_kph: Double? =null,
    val gust_mph: Double? =null,
    val heatindex_c: Double? =null,
    val heatindex_f: Double? =null,
    val condition: Condition? =null,
    val humidity: Double? =null,
    val is_day: Int? =null,
    val last_updated: String? =null,
    val last_updated_epoch: Int? =null,
    val precip_in: Double? =null,
    val precip_mm: Double? =null,
    val pressure_in: Double? =null,
    val pressure_mb: Double? =null,
    val temp_c: Double? =null,
    val temp_f: Double? =null,
    val uv: Double? =null,
    val vis_km: Double? =null,
    val vis_miles: Double? =null,
    val wind_degree: Int? =null,
    val wind_dir: String? =null,
    val wind_kph: Double? =null,
    val wind_mph: Double? =null,
    val windchill_c: Double? =null,
    val windchill_f: Double? =null
)