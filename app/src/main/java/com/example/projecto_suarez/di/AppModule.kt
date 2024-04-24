package com.example.projecto_suarez.di

import android.app.Application
import com.example.projecto_suarez.data.manager.LocalUserManagerImpl
import com.example.projecto_suarez.data.remote.NewsApi
import com.example.projecto_suarez.data.repository.NewsRepositoryImpl
import com.example.projecto_suarez.domain.manager.LocalUserManager
import com.example.projecto_suarez.domain.repository.NewsRepository
import com.example.projecto_suarez.domain.usescases.app_entry.AppEntryUseCases
import com.example.projecto_suarez.domain.usescases.app_entry.ReadAppEntry
import com.example.projecto_suarez.domain.usescases.app_entry.SaveAppEntry
import com.example.projecto_suarez.domain.usescases.news.GetNews
import com.example.projecto_suarez.domain.usescases.news.NewsUseCases
import com.example.projecto_suarez.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
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
        newsApi: NewsApi
    ) : NewsRepository = NewsRepositoryImpl(newsApi)
    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository
    ) : NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(newsRepository)
        )
    }
}