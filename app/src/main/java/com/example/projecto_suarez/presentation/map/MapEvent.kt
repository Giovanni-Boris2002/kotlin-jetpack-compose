package com.example.projecto_suarez.presentation.map

import com.example.projecto_suarez.domain.model.Article


sealed class MapEvent {
    data class RedirectoToDetails(
        val qrResult: String,
        val navigateToDetails: (Article) -> Unit
    ): MapEvent()
}