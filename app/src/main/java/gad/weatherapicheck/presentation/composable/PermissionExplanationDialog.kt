package gad.weatherapicheck.presentation.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gad.weatherapicheck.R

@Composable
fun PermissionExplanationDialog(onDismiss: () -> Unit, onGrantPermission: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.permission_required)) },
        text = { Text(stringResource(R.string.permission_explanation)) },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009688) , contentColor = Color.White),
                onClick = onGrantPermission
            ) {
                Text(stringResource(R.string.grant_permission))
            }
        },
        dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009688) , contentColor = Color.White),
                onClick = onDismiss
            ) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

@Composable
fun SettingsRedirectDialog(onDismiss: () -> Unit, onOpenSettings: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.permission_denied)) },
        text = { Text(stringResource(R.string.permission_denied_explanation)) },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009688) , contentColor = Color.White),
                onClick = onOpenSettings
            ) {
                Text(stringResource(R.string.open_settings))
            }
        },
        dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009688) , contentColor = Color.White),
                onClick = onDismiss
            ) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

@Composable
fun RequestPermissionScreen(onRequestPermission: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.permission_request_screen_text),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009688) , contentColor = Color.White),
            onClick = onRequestPermission
        ) {
            Text(stringResource(R.string.request_permission))
        }
    }
}
