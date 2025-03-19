package gad.weatherapicheck.presentation.ui.weatherhistory

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import gad.weatherapicheck.R
import gad.weatherapicheck.domain.entities.TempImageEntity
import gad.weatherapicheck.presentation.composable.RTLComposable
import gad.weatherapicheck.presentation.ui.weatherdata.WeatherKeyVal
import gad.weatherapicheck.presentation.viewmodel.ImageViewModel

@Composable
fun WeatherHistory(imageViewModel: ImageViewModel? = null) {

    val weatherHistory = imageViewModel?.weatherHistory?.collectAsStateWithLifecycle()?.value

    DisposableEffect(key1 = Unit) {
        imageViewModel?.fetchImages()
        onDispose {
            imageViewModel?.resetImages()
        }
    }

    BackHandler {
        imageViewModel?.onBackPressed()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        weatherHistory?.forEachIndexed { _, tempImageEntity ->
            item {
                HistoryItem(tempImageEntity = tempImageEntity)
            }
        }
    }
}

@Composable
fun HistoryItem(tempImageEntity: TempImageEntity) {
    RTLComposable {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp)
                .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                .background(Color.White)
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.label_temp, "${tempImageEntity.temp}"),
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Box(
                contentAlignment = Alignment.Center
            ) {
                Log.i("WeatherHistory", "HistoryItem: ${tempImageEntity.uri}")
                AsyncImage(
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.TopStart),
                    model = tempImageEntity.uri,
                    placeholder = painterResource(android.R.drawable.stat_notify_sync),
                    error = painterResource(android.R.drawable.stat_notify_error),
                    contentDescription = "Condition icon"
                )
                AsyncImage(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.TopStart),
                    model = tempImageEntity.weatherStateImage,
                    contentDescription = "Condition icon"
                )
            }



            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                WeatherKeyVal(
                    stringResource(R.string.label_speed_value, "${tempImageEntity.wind}"),
                    stringResource(R.string.label_wind)
                )
                WeatherKeyVal(
                    "${tempImageEntity.humidity} %", stringResource(R.string.label_humidity)
                )
                WeatherKeyVal(
                    stringResource(R.string.label_pressure_unit, "${tempImageEntity.pressure}"),
                    stringResource(R.string.label_pressure)
                )
            }
        }
    }
}