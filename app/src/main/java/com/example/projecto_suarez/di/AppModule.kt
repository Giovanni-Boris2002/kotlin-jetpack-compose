package com.example.projecto_suarez.di

import android.app.Application
import com.example.projecto_suarez.data.manager.LocalUserManagerImpl
import com.example.projecto_suarez.domain.manager.LocalUserManager
import com.example.projecto_suarez.domain.usescases.AppEntryUseCases
import com.example.projecto_suarez.domain.usescases.ReadAppEntry
import com.example.projecto_suarez.domain.usescases.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
}