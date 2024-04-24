package com.example.projecto_suarez.domain.usescases.news

import androidx.paging.PagingData
import com.example.projecto_suarez.domain.model.Article
import com.example.projecto_suarez.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNews(
    private val newsRepository: NewsRepository
) {
    operator fun invoke( sources: List<String>): Flow<PagingData<Article>> {
        return newsRepository.getNews(sources = sources)
    }
}