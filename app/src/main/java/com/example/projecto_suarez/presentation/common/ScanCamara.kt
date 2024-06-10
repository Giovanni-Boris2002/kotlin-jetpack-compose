package com.example.projecto_suarez.presentation.common

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Icon
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import com.example.projecto_suarez.CaptureActivityPortrait
import com.example.projecto_suarez.R
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

@Composable
fun ScanCamara(modifier: Modifier = Modifier, onClick: () -> Unit) {
    var resultScan by remember { mutableStateOf("") }
    val scanLauncher = rememberLauncherForActivityResult(
        contract = ScanContract(),
        onResult = {
            resultScan = it.contents?: "Sin resultado"
            Log.d("Camera",resultScan)
        }
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 10.dp, bottom = 15.dp),
        contentAlignment = Alignment.BottomEnd)
    {
        IconButton(
            modifier = modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(colorResource(id = R.color.body)),
            onClick = {
                val options = ScanOptions()
                options.setOrientationLocked(false);
                options.setCaptureActivity(CaptureActivityPortrait::class.java)
                options.setBeepEnabled(false)
                scanLauncher.launch(options)
            }
        ) {
            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = "Camera Icon",
                tint = Color.White
            )
        }
    }

}