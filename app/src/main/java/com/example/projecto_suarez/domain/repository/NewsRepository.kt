package com.example.projecto_suarez.domain.repository

import com.example.projecto_suarez.domain.model.Article
import kotlinx.coroutines.flow.Flow
import androidx.paging.PagingData

interface NewsRepository {
    fun getNews(sources: List<String>): Flow<PagingData<Article>>
    fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>>
    suspend fun upsertArticle(article: Article)

    suspend fun deleteArticle(article: Article)
    fun getArticles(): Flow<List<Article>>

    suspend fun getArticle(url: String): Article?

}