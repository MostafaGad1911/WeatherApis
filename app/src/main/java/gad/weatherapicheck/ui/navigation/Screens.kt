package gad.weatherapicheck.ui.navigation

sealed class Screens(val route: String) {
    data object MyLocation : Screens("my_location")

    data object WeatherHistory : Screens("weather_history")
}