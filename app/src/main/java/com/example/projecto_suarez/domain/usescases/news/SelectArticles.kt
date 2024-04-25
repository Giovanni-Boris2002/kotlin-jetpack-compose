package com.example.projecto_suarez.domain.usescases.news

import com.example.projecto_suarez.data.local.NewsDao
import com.example.projecto_suarez.domain.model.Article
import com.example.projecto_suarez.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SelectArticles (
    private val newsRepository: NewsRepository
) {
    operator fun invoke(): Flow<List<Article>>{
        return newsRepository.getArticles()
    }
}