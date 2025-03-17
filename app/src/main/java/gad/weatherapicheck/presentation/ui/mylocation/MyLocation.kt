package gad.weatherapicheck.presentation.ui.mylocation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Preview
@Composable
fun MyLocation(
    viewModel: LocationViewModel? = null,
    weatherViewModel: WeatherViewModel? = null
) {
    val weatherState = weatherViewModel?.weatherState?.collectAsStateWithLifecycle()?.value
    val errorMessage = weatherViewModel?.errorMessage?.collectAsStateWithLifecycle()?.value
    val locationState = viewModel?.locationState?.collectAsStateWithLifecycle()?.value
    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(key1 = Unit, key2 = locationState) {
        coroutineScope.launch {
            viewModel?.fetchLocation()
            if (locationState != null) {
                weatherViewModel?.fetchWeather(locationState.latitude, locationState.longitude)
            }
        }
        onDispose {
            coroutineScope.launch {
                weatherViewModel?.resetErrorMessage()
            }
        }
    }





    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(all = 40.dp), horizontalArrangement = Arrangement.End
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
                        weatherViewModel.resetErrorMessage()
                    }
                )
            }
        }, modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(all = 15.dp)
                .fillMaxSize()
                .padding(innerPadding), contentAlignment = Alignment.Center
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

