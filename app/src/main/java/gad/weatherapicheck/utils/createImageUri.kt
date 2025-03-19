package gad.weatherapicheck.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File

fun createImageUri(context: Context): Uri? {
    val imagesDir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "MyAppImages")
    if (!imagesDir.exists()) imagesDir.mkdirs()

    val file = File(imagesDir, "${System.currentTimeMillis()}.jpg")
    return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
}
