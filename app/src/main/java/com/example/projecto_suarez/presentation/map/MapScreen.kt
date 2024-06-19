package com.example.projecto_suarez.presentation.map

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.projecto_suarez.domain.model.Article
import com.example.projecto_suarez.presentation.common.ScanCamara

@Composable
fun MapScreen(
    result: String,
    state: MapState,
    event: (MapEvent) -> Unit,
    navigateToDetails: (Article) -> Unit) {
    ScanCamara(
        onResult = { event(MapEvent.RedirectoToDetails(it, navigateToDetails))}
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Detected Beacons:")
        Text(result)
        Spacer(modifier = Modifier.height(8.dp))
        state.beacons?.forEach { beacon ->
            Text(text = beacon)
            Spacer(modifier = Modifier.height(4.dp))
        }

    }
    Text("Detected Beacons:")
}