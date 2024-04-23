package com.example.projecto_suarez.domain.repository

import com.example.projecto_suarez.domain.model.Article
import kotlinx.coroutines.flow.Flow
import androidx.paging.PagingData

interface NewsRepository {
    fun getNews(sources: List<String>): Flow<PagingData<Article>>
}