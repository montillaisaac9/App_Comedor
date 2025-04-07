package com.example.app_comedor.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

// Función para crear un archivo temporal en el cacheDir
fun createImageFile(context: Context): File {
    return File.createTempFile("IMG_${System.currentTimeMillis()}", ".jpg", context.cacheDir)
}

// Función para obtener el Uri de un archivo mediante FileProvider
fun getUriForFile(context: Context, file: File): Uri {
    return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
}

// Función para copiar el contenido de un Uri a un archivo temporal y retornarlo
fun uriToFile(context: Context, uri: Uri): File? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val tempFile = File.createTempFile("IMG_${System.currentTimeMillis()}", ".jpg", context.cacheDir)
        FileOutputStream(tempFile).use { outputStream ->
            inputStream.copyTo(outputStream)
        }
        tempFile
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
