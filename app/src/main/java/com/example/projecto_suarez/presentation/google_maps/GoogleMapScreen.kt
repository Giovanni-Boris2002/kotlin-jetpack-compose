package com.example.projecto_suarez.presentation.google_maps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.projecto_suarez.presentation.Dimens
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
@Composable
fun GoogleMapScreen() {
    Column(
        modifier = Modifier
            .padding(
                top = Dimens.MediumPadding1,
                start = Dimens.MediumPadding1,
                end = Dimens.MediumPadding1
            )
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(-16.398829,-71.5394782), 10f) //posición original ubicada en plaza de armas
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            val gallery1 = LatLng(-16.397658, -71.537468) // Centro Cultural Unsa
            val gallery2 = LatLng(-16.3964811,-71.539033) // Alianza Francesa Arequipa
            val gallery3 = LatLng(-16.4063731,-71.5249423) //EPIS

            Marker(
                state = com.google.maps.android.compose.MarkerState(position = gallery1),
                title = "Galería 1",
                snippet = "Información de la Galería 1"
            )
            Marker(
                state = com.google.maps.android.compose.MarkerState(position = gallery2),
                title = "Galería 2",
                snippet = "Información de la Galería 2"
            )
            Marker(
                state = com.google.maps.android.compose.MarkerState(position = gallery3),
                title = "Galería 3",
                snippet = "Información de la Galería 3"
            )
        }
    }
}