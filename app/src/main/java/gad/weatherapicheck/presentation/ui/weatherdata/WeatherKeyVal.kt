package gad.weatherapicheck.presentation.ui.weatherdata

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
