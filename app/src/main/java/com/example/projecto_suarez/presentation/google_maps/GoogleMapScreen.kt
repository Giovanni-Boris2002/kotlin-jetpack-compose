package com.example.projecto_suarez.presentation.google_maps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            position = CameraPosition.fromLatLngZoom(LatLng(-16.398829, -71.5394782), 15f) // posición original ubicada en plaza de armas
        }
        Text(
            text = "Mapa de Galerías de Arequipa",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            val gallery1 = LatLng(-16.397658, -71.537468) // Centro Cultural Unsa
            val gallery2 = LatLng(-16.3964811,-71.539033) // Alianza Francesa Arequipa
            val gallery3 = LatLng(-16.4063731,-71.5249423) //EPIS

            Marker(
                state = com.google.maps.android.compose.MarkerState(position = gallery1),
                title = "Centro Cultural UNSA",
                snippet = "Bienvenido al Centro cultural de la Universidad Nacional de San Agustín."
            )
            Marker(
                state = com.google.maps.android.compose.MarkerState(position = gallery2),
                title = "Alianza Francesa Arequipa",
                snippet = "Bienvenido a la Alianza Francesa en Arequipa."
            )
            Marker(
                state = com.google.maps.android.compose.MarkerState(position = gallery3),
                title = "EPIS",
                snippet = "Bienvenido a nuestra galería de prueba en la Escuela Profesional de Ingeniería de Sistemas."
            )
        }
    }
}