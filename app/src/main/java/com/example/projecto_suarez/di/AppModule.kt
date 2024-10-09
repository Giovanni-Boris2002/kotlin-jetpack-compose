package com.example.projecto_suarez.di

import android.app.Application

import com.example.projecto_suarez.data.manager.LocalUserManagerImpl
import com.example.projecto_suarez.data.remote.CloudApi
import com.example.projecto_suarez.data.repository.CloudRepositoryImpl
import com.example.projecto_suarez.domain.manager.LocalUserManager
import com.example.projecto_suarez.domain.repository.CloudRepository
import com.example.projecto_suarez.domain.usescases.app_entry.AppEntryUseCases
import com.example.projecto_suarez.domain.usescases.app_entry.ReadAppEntry
import com.example.projecto_suarez.domain.usescases.app_entry.SaveAppEntry
import com.example.projecto_suarez.domain.usescases.cloud.ApiUseCases
import com.example.projecto_suarez.domain.usescases.cloud.EnrolledLab
import com.example.projecto_suarez.domain.usescases.cloud.GetCourses
import com.example.projecto_suarez.domain.usescases.cloud.GetLabs
import com.example.projecto_suarez.domain.usescases.cloud.UnregisterLab
import com.example.projecto_suarez.util.Constants.BASE_URL

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
    fun provideNewsApi(): CloudApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CloudApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCloudRepository(
        cloudApi: CloudApi
    ) : CloudRepository = CloudRepositoryImpl(cloudApi)

    @Provides
    @Singleton
    fun provideApiUseCases(
        cloudRepository: CloudRepository
    ) : ApiUseCases {
        return ApiUseCases(
            getCourses = GetCourses(cloudRepository),
            getLabs = GetLabs(cloudRepository),
            enrolledLab = EnrolledLab(cloudRepository),
            unregisterLab = UnregisterLab(cloudRepository)
        )
    }

}