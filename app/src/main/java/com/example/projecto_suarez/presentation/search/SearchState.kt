package com.example.projecto_suarez.presentation.search

import androidx.paging.PagingData
import com.example.projecto_suarez.domain.model.Article
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String = "",
    val articles: Flow<PagingData<Article>>? = null
)