package gad.weatherapicheck.presentation.ui.mylocation

import android.app.Activity
import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import gad.weatherapicheck.R
import gad.weatherapicheck.presentation.composable.ErrorSnackBar
import gad.weatherapicheck.presentation.ui.weatherdata.WeatherDetails
import gad.weatherapicheck.presentation.viewmodel.LocationViewModel
import gad.weatherapicheck.presentation.viewmodel.WeatherViewModel
import gad.weatherapicheck.ui.navigation.Screens
import gad.weatherapicheck.utils.GPSUtils
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun MyLocation(
    activity: Activity,
    viewModel: LocationViewModel? = null,
    weatherViewModel: WeatherViewModel? = null
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var isGpsEnabled by remember { mutableStateOf(GPSUtils.isGpsEnabled(context)) }

    val gpsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        isGpsEnabled = GPSUtils.isGpsEnabled(context)
        if (isGpsEnabled) {
            coroutineScope.launch {
                viewModel?.fetchLocation()
            }
        }
    }

    LaunchedEffect(Unit) {
        if (!isGpsEnabled) {
            Toast.makeText(context, "Please enable GPS to get location", Toast.LENGTH_LONG).show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            gpsLauncher.launch(intent)
        } else {
            viewModel?.fetchLocation()
        }
    }

    val locationState = viewModel?.locationState?.collectAsStateWithLifecycle()?.value
    val weatherState = weatherViewModel?.weatherState?.collectAsStateWithLifecycle()?.value
    val errorMessage = weatherViewModel?.errorMessage?.collectAsStateWithLifecycle()?.value

    LaunchedEffect(locationState) {
        locationState?.let {
            weatherViewModel?.fetchWeather(it.latitude, it.longitude)
        }
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp, vertical = 8.dp), horizontalArrangement = Arrangement.End
            ) {
                Image(
                    modifier = Modifier
                        .size(35.dp)
                        .clickable {
                            viewModel?.navigate(Screens.WeatherHistory.route)
                        },
                    painter = painterResource(id = R.drawable.baseline_history_24),
                    contentDescription = "Location",
                )
            }
        },
        snackbarHost = {
            if (errorMessage != null) {
                ErrorSnackBar(
                    message = errorMessage,
                    onDismiss = {
                        weatherViewModel?.resetErrorMessage()
                    }
                )
            }
        }, modifier = Modifier.fillMaxSize()
            .statusBarsPadding()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(innerPadding)
                .fillMaxSize()
                .padding(WindowInsets.systemBars.asPaddingValues())
                , contentAlignment = Alignment.Center
        ) {
            if (weatherState != null) {
                WeatherDetails(data = weatherState)
            } else {
                CircularProgressIndicator(
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}


