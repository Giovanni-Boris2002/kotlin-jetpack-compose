package com.example.projecto_suarez.di

import android.app.Application
import androidx.room.Room
import com.example.projecto_suarez.data.local.NewsDao
import com.example.projecto_suarez.data.local.NewsDatabase
import com.example.projecto_suarez.data.local.NewsTypeConvertor
import com.example.projecto_suarez.data.manager.LocalUserManagerImpl
import com.example.projecto_suarez.data.remote.NewsApi
import com.example.projecto_suarez.data.repository.NewsRepositoryImpl
import com.example.projecto_suarez.domain.manager.LocalUserManager
import com.example.projecto_suarez.domain.repository.NewsRepository
import com.example.projecto_suarez.domain.usescases.app_entry.AppEntryUseCases
import com.example.projecto_suarez.domain.usescases.app_entry.ReadAppEntry
import com.example.projecto_suarez.domain.usescases.app_entry.SaveAppEntry
import com.example.projecto_suarez.domain.usescases.news.DeleteArticle
import com.example.projecto_suarez.domain.usescases.news.GetNews
import com.example.projecto_suarez.domain.usescases.news.NewsUseCases
import com.example.projecto_suarez.domain.usescases.news.SearchNews
import com.example.projecto_suarez.domain.usescases.news.SelectArticle
import com.example.projecto_suarez.domain.usescases.news.SelectArticles
import com.example.projecto_suarez.domain.usescases.news.UpsertArticle
import com.example.projecto_suarez.services.BeaconScanner
import com.example.projecto_suarez.util.Constants.BASE_URL
import com.example.projecto_suarez.util.Constants.NEWS_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManger(
        application: Application
    ): LocalUserManager = LocalUserManagerImpl(application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    ) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi,
        newsDao: NewsDao
    ) : NewsRepository = NewsRepositoryImpl(newsApi, newsDao)
    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository
    ) : NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(newsRepository),
            searchNews = SearchNews(newsRepository),
            upsertArticle = UpsertArticle(newsRepository),
            deleteArticle = DeleteArticle(newsRepository),
            selectArticles = SelectArticles(newsRepository),
            selectArticle = SelectArticle(newsRepository)
        )
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ) : NewsDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = NEWS_DATABASE_NAME
        ).addTypeConverter(NewsTypeConvertor())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewsDatabase
    ) : NewsDao = newsDatabase.newsDao

    @Provides
    @Singleton
    fun provideBeaconScanner(
        application: Application
    ) : BeaconScanner = BeaconScanner(application)
}