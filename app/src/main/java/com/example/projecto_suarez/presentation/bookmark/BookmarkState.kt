package com.example.projecto_suarez.presentation.bookmark

import com.example.projecto_suarez.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList()
)
