package com.example.projecto_suarez.presentation.map

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projecto_suarez.presentation.common.ScanCamara
import org.altbeacon.beacon.RegionViewModel

@Composable
fun MapScreen(state: MapState) {
    ScanCamara(){

    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Detected Beacons:")
        Spacer(modifier = Modifier.height(8.dp))
        state.beacons?.forEach { beacon ->
            Text(text = beacon)
            Spacer(modifier = Modifier.height(4.dp))
        }

    }
    Text("Detected Beacons:")
}