package com.example.projecto_suarez.domain.repository

import com.example.projecto_suarez.domain.model.Article
import kotlinx.coroutines.flow.Flow
import androidx.paging.PagingData
import com.example.projecto_suarez.data.remote.dto.NewsResponse

interface NewsRepository {
    fun getNews(sources: List<String>): Flow<PagingData<Article>>
    fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>>
    suspend fun getNewsById(sources: List<String>, id: Int): NewsResponse
    suspend fun upsertArticle(article: Article)

    suspend fun deleteArticle(article: Article)
    fun getArticles(): Flow<List<Article>>

    suspend fun getArticle(url: String): Article?

}