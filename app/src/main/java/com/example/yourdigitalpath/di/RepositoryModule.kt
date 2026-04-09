package com.example.yourdigitalpath.di

import com.example.yourdigitalpath.data.repositoryImp.ServiceRequestRepoImpl
import com.example.yourdigitalpath.domain.repository.ServiceRequestRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRequestRepository(
        serviceRequestRepoImpl: ServiceRequestRepoImpl
    ): ServiceRequestRepository
}