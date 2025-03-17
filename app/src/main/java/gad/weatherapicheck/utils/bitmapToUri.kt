package gad.weatherapicheck.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import java.io.ByteArrayOutputStream

fun bitmapToUri(context: Context, bitmap: Bitmap): Uri? {
    val bytes = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(
        context.contentResolver,
        bitmap,
        "IMG_${System.currentTimeMillis()}",
        null
    )
    return path?.let { Uri.parse(it) }
}
