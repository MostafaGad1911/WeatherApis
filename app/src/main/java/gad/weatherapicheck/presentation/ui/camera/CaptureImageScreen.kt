package gad.weatherapicheck.presentation.ui.camera

import androidx.compose.runtime.Composable

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import java.io.File

@Composable
fun CaptureImageScreen() {
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val activity = context as ComponentActivity

    val captureImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                imageUri.value?.let {
                    Toast.makeText(context, "Image captured: $it", Toast.LENGTH_SHORT).show()
                }
            }
        }

    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                captureImageLauncher.launch(imageUri.value!!)
            } else {
                Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }


    val file = remember {
        File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "captured_${System.currentTimeMillis()}.jpg")
    }
    val uri = remember { FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        imageUri.value?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = "Captured Image",
                modifier = Modifier.size(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                imageUri.value = uri
                captureImageLauncher.launch(uri)
            } else {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }) {
            Text("Capture Image")
        }
    }
}
