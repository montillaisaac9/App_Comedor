package com.example.app_comedor.presentacion.screens.auth.register.components

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ListItem as M3ListItem
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.core.content.FileProvider
import com.example.app_comedor.utils.createImageFile
import com.example.app_comedor.utils.getUriForFile
import com.example.app_comedor.utils.uriToFile
import java.io.File
import java.io.FileOutputStream


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TakeCapPicture(
    modifier: Modifier = Modifier,
    imageFile: File? = null,
    onImageSelected: (File) -> Unit
) {
    val context = LocalContext.current

    // Estado para mostrar el ModalBottomSheet
    var showBottomSheet by remember { mutableStateOf(false) }

    // Creamos un archivo temporal para la cámara y obtenemos su Uri mediante FileProvider
    val tempImageFile = remember { createImageFile(context) }
    val tempImageUri = remember { getUriForFile(context, tempImageFile) }

    // Launcher para cámara (usando TakePicture)
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            onImageSelected(tempImageFile)
        } else {
            Toast.makeText(context, "Error al tomar la foto", Toast.LENGTH_LONG).show()
        }
    }

    // Launcher para seleccionar imagen de la galería
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri == null) {
            Toast.makeText(context, "No se seleccionó ninguna imagen", Toast.LENGTH_LONG).show()
        } else {
            val fileFromGallery = uriToFile(context, uri)
            if (fileFromGallery != null) {
                onImageSelected(fileFromGallery)
            } else {
                Toast.makeText(context, "Error al procesar la imagen", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Launcher para solicitar permiso de cámara
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch(tempImageUri)
        } else {
            Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_LONG).show()
        }
    }

    // Componente que muestra la imagen (o ícono) y es clickable para abrir el BottomSheet
    Box(
        modifier = modifier
            .size(150.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable { showBottomSheet = true },
        contentAlignment = Alignment.Center
    ) {
        if (imageFile != null) {
            AsyncImage(
                model = imageFile,
                contentDescription = "Imagen seleccionada",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Icon(
                imageVector = Icons.Outlined.CloudUpload,
                contentDescription = "Subir foto",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(48.dp)
            )
        }
    }

    // Mostrar ModalBottomSheet con las opciones de cámara y galería
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Elige una opción", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                // Opción: Tomar foto
                M3ListItem(
                    modifier = Modifier.clickable {
                        showBottomSheet = false
                        if (context.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                            cameraLauncher.launch(tempImageUri)
                        } else {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    },
                    headlineContent = { Text("Tomar foto") },
                    leadingContent = { Icon(Icons.Filled.CameraAlt, contentDescription = null) }
                )
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
                M3ListItem(
                    modifier = Modifier.clickable {
                        showBottomSheet = false
                        galleryLauncher.launch("image/*")
                    },
                    headlineContent = { Text("Seleccionar de la galería") },
                    leadingContent = { Icon(Icons.Filled.PhotoLibrary, contentDescription = null) }
                )
            }
        }
    }
}