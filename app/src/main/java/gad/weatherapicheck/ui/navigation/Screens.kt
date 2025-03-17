package gad.weatherapicheck.ui.navigation

sealed class Screens(val route: String) {
    object MyLocation : Screens("my_location")

    object WeatherHistory : Screens("weather_history")
}