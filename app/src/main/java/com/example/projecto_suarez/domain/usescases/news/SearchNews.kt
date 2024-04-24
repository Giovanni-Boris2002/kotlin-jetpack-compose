package com.example.projecto_suarez.domain.usescases.news

import androidx.paging.PagingData
import com.example.projecto_suarez.domain.model.Article
import com.example.projecto_suarez.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SearchNews(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(searchQuery: String ,sources: List<String>): Flow<PagingData<Article>> {
        return newsRepository.searchNews(searchQuery = searchQuery, sources = sources)
    }
}