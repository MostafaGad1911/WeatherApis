package gad.weatherapicheck.presentation.ui.weatherdata

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import gad.weatherapicheck.R
import gad.weatherapicheck.data.model.WeatherResponse
import gad.weatherapicheck.domain.entities.TempImageEntity
import gad.weatherapicheck.presentation.composable.RTLComposable
import gad.weatherapicheck.presentation.viewmodel.ImageViewModel
import gad.weatherapicheck.presentation.viewmodel.LocationViewModel
import gad.weatherapicheck.utils.bitmapToUri
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel


@OptIn(ExperimentalPermissionsApi::class)
@Preview(showBackground = true)
@Composable
fun WeatherDetails(
    data: WeatherResponse? = null,
    imagesViewModel: ImageViewModel = getViewModel(),
) {
    val offsetX = remember { Animatable(-1000f) }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val imageBitmap = remember { mutableStateOf<Bitmap?>(null) }

    val cameraPermissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA
    )

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            imageBitmap.value = it
        }
    }

    LaunchedEffect(imageBitmap.value) {
        data?.let {
            imageBitmap.value?.let { bitmap ->
                val uri = bitmapToUri(context = context, bitmap = bitmap)
                imagesViewModel.insertImage(
                    image = TempImageEntity(
                        uri = uri.toString(),
                        temp = it.current?.temp_c ?: 0.0,
                        wind = it.current?.wind_kph ?: 0.0,
                        humidity = it.current?.humidity ?: 0.0,
                        pressure = it.current?.pressure_mb ?: 0.0,
                        weatherStateImage = "https:${data.current?.condition?.icon}".replace("64x64", "128x128")
                    )
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        scope.launch {
            offsetX.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 600, easing = LinearOutSlowInEasing)
            )
        }
    }

    RTLComposable {
        Column(
            modifier = Modifier
                .offset { IntOffset(offsetX.value.toInt(), 0) }
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp)
                .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                .background(Color.White)
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location icon",
                    modifier = Modifier.size(40.dp),
                    tint = Color.Red
                )
                Column {
                    Text(
                        text = data?.location?.name ?: "-",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp
                    )
                    Text(
                        text = data?.location?.country ?: "-",
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.label_temp, data?.current?.temp_c ?: "-"),
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Box(
                modifier = Modifier.clickable {
                    if (cameraPermissionState.status.isGranted) {
                        launcher.launch(null)
                    } else {
                        cameraPermissionState.launchPermissionRequest()
                    }
                },
                contentAlignment = Alignment.Center
            ) {
                imageBitmap.value?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Captured image",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                } ?: Image(
                    painter = painterResource(R.drawable.baseline_linked_camera_24),
                    contentDescription = "Camera icon",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(bottom = 8.dp)
                )

                AsyncImage(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.TopStart),
                    model = "https:${data?.current?.condition?.icon}".replace("64x64", "128x128"),
                    contentDescription = "Condition icon"
                )
            }

            Text(
                text = data?.current?.condition?.text ?: "-",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                WeatherKeyVal(
                    stringResource(R.string.label_speed_value, data?.current?.wind_kph ?: "-"),
                    stringResource(R.string.label_wind)
                )
                WeatherKeyVal(
                    "${data?.current?.humidity ?: "-"} %", stringResource(R.string.label_humidity)
                )
                WeatherKeyVal(
                    stringResource(R.string.label_pressure_unit, data?.current?.pressure_mb ?: "-"),
                    stringResource(R.string.label_pressure)
                )
            }
        }
    }
}


@Composable
fun WeatherKeyVal(key: String, value: String) {
    RTLComposable {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = value, fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = key, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)
        }

    }
}














