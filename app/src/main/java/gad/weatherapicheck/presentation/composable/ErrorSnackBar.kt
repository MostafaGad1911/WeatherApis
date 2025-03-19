package gad.weatherapicheck.presentation.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import gad.weatherapicheck.R

@Composable
fun ErrorSnackBar(
    message: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    actionLabel: String? = stringResource(R.string.action_cancel)
) {
    Snackbar(
        modifier = modifier.padding(16.dp),
        action = {
            if (!actionLabel.isNullOrEmpty()) {
                TextButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = White
                    )
                }
            }
        },
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            ) , color = White
        )
    }
}
