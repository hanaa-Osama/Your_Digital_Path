package com.example.yourdigitalpath.di

import com.example.yourdigitalpath.data.repositoryImp.UploadRepositoryImpl
import com.example.yourdigitalpath.domain.repository.UploadRepository

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideUploadRepository(impl: UploadRepositoryImpl): UploadRepository = impl
}