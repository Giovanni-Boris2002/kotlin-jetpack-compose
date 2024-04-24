package com.example.projecto_suarez.domain.usescases.news

import com.example.projecto_suarez.data.local.NewsDao
import com.example.projecto_suarez.domain.model.Article

class DeleteArticle (
    private val newsDao: NewsDao
) {
    suspend operator fun invoke(article: Article){
        newsDao.delete(article)
    }
}