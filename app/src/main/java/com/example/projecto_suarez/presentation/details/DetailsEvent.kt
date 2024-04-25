package com.example.projecto_suarez.presentation.details

import com.example.projecto_suarez.domain.model.Article

sealed class DetailsEvent {
    data class UpsertDeleteArticle(val article: Article): DetailsEvent()
    object RemoveSideEffect: DetailsEvent()
}