package com.example.projecto_suarez.domain.usescases.news

import com.example.projecto_suarez.data.local.NewsDao
import com.example.projecto_suarez.domain.model.Article
import kotlinx.coroutines.flow.Flow

class SelectArticles (
    private val newsDao: NewsDao
) {
    operator fun invoke(): Flow<List<Article>>{
        return newsDao.getArticles()
    }
}