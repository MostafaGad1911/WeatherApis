package gad.weatherapicheck.presentation.ui.weatherdata

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gad.weatherapicheck.presentation.composable.RTLComposable

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
