package gad.weatherapicheck.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import gad.weatherapicheck.presentation.composable.PermissionExplanationDialog
import gad.weatherapicheck.presentation.composable.RequestPermissionScreen
import gad.weatherapicheck.presentation.composable.SettingsRedirectDialog
import gad.weatherapicheck.presentation.ui.mylocation.MyLocation
import gad.weatherapicheck.presentation.ui.weatherhistory.WeatherHistory
import gad.weatherapicheck.presentation.viewmodel.ImageViewModel
import gad.weatherapicheck.presentation.viewmodel.LocationViewModel
import gad.weatherapicheck.presentation.viewmodel.WeatherViewModel
import gad.weatherapicheck.ui.navigation.Screens
import gad.weatherapicheck.ui.theme.WeatherApiCheckTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {

    private lateinit var locationPermissionLauncher: ActivityResultLauncher<Array<String>>
    private var hasPermissions by mutableStateOf(false)
    private var showPermissionDialog by mutableStateOf(false)
    private var showSettingsDialog by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
            val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

            hasPermissions = fineLocationGranted && coarseLocationGranted

            if (!hasPermissions) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) ||
                    shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
                ) {
                    showPermissionDialog = true
                } else {
                    showSettingsDialog = true
                }
            }
        }

        enableEdgeToEdge()
        setContent {
            WeatherApiCheckTheme {
                if (hasPermissions) {
                    val navController = rememberNavController()
                    val imageViewModel = getViewModel<ImageViewModel>()
                    val locationViewModel = getViewModel<LocationViewModel>()
                    val weatherViewModel = getViewModel<WeatherViewModel>()

                    NavHost(
                        navController = navController,
                        startDestination = Screens.MyLocation.route
                    ) {
                        composable(Screens.MyLocation.route) {
                            MyLocation(
                                viewModel = locationViewModel,
                                weatherViewModel = weatherViewModel,
                                activity = this@MainActivity
                            )
                        }
                        composable(Screens.WeatherHistory.route) {
                            WeatherHistory(imageViewModel = imageViewModel)
                        }
                    }
                } else {
                    RequestPermissionScreen { requestLocationPermissions() }
                }

                if (showPermissionDialog) {
                    PermissionExplanationDialog(
                        onDismiss = { showPermissionDialog = false },
                        onGrantPermission = { requestLocationPermissions() }
                    )
                }

                if (showSettingsDialog) {
                    SettingsRedirectDialog(
                        onDismiss = { showSettingsDialog = false },
                        onOpenSettings = { openAppSettings() }
                    )
                }
            }
        }
    }

    private fun requestLocationPermissions() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (permissions.all { checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED }) {
            hasPermissions = true
        } else {
            locationPermissionLauncher.launch(permissions)
        }
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        }
        startActivity(intent)
    }
}



