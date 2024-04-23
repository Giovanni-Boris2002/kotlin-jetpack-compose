package com.example.projecto_suarez.data.remote.dto

import com.example.projecto_suarez.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)