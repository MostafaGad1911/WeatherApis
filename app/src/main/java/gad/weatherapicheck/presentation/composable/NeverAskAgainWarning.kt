package gad.weatherapicheck.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import gad.weatherapicheck.R

@Composable
    fun NeverAskAgainWarning(
        onOpenSettings: () -> Unit,
        onDismiss: () -> Unit
    ) {
        AlertDialog(
            onDismissRequest = {},
            title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    text = stringResource(id = R.string.label_confirm_permissions),
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    color = Color.Black
                )
            },
            text = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
                    text = stringResource(id = R.string.label_app_permissions_warning_msg),
                    color = Color.Black
                )
            },
            confirmButton = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    TextButton(onClick = {
                        onDismiss.invoke()
                    }) {
                        Text(
                            text = stringResource(id = R.string.action_cancel),
                            color = Color.Gray,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = { onOpenSettings() }) {
                        Text(
                            text = stringResource(id = R.string.action_ok), color = Color.Gray,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
                        )
                    }
                }
            },
            containerColor = Color.White
        )
    }