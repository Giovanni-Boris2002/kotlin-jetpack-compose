package com.example.projecto_suarez.domain.usescases.news

import com.example.projecto_suarez.data.local.NewsDao
import com.example.projecto_suarez.domain.model.Article
import com.example.projecto_suarez.domain.repository.NewsRepository

class SelectArticle (
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(url: String): Article?{
        return newsRepository.getArticle(url)
    }
}