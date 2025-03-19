package gad.weatherapicheck.ui

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import gad.weatherapicheck.presentation.composable.NeverAskAgainWarning
import gad.weatherapicheck.presentation.ui.mylocation.MyLocation
import gad.weatherapicheck.presentation.ui.weatherhistory.WeatherHistory
import gad.weatherapicheck.presentation.viewmodel.ImageViewModel
import gad.weatherapicheck.presentation.viewmodel.LocationViewModel
import gad.weatherapicheck.presentation.viewmodel.WeatherViewModel
import gad.weatherapicheck.ui.navigation.Screens
import gad.weatherapicheck.ui.theme.WeatherApiCheckTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {

    private lateinit var locationPermissionLauncher: ActivityResultLauncher<Array<String>>

    private var hasPermissions by mutableStateOf(false)
    private var showRationale by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            hasPermissions = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        }


        enableEdgeToEdge()
        setContent {
            WeatherApiCheckTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val coroutineScope = rememberCoroutineScope()

                val imageViewModel = getViewModel<ImageViewModel> {
                    parametersOf()
                }.apply {
                    updateNavController(navController = navController)
                    parametersOf(navController)
                }

                val locationViewModel = getViewModel<LocationViewModel> {
                    parametersOf()
                }.apply {
                    updateNavController(navController = navController)
                    parametersOf(navController)
                }

                val weatherViewModel = getViewModel<WeatherViewModel> {
                    parametersOf()
                }.apply {
                    updateNavController(navController = navController)
                    parametersOf(navController)
                }

                NavHost(
                    navController = navController,
                    startDestination = Screens.MyLocation.route
                ) {
                    composable(Screens.MyLocation.route) {
                        MyLocation(
                            viewModel = locationViewModel,
                            weatherViewModel = weatherViewModel ,
                            activity = this@MainActivity
                        )
                    }
                    composable(Screens.WeatherHistory.route) {
                        WeatherHistory(
                            imageViewModel = imageViewModel
                        )
                    }
                }
            }
        }
    }


}
