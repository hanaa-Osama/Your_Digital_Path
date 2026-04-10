package com.example.yourdigitalpath.di

import com.example.yourdigitalpath.data.repositoryImp.OrderStatusRepositoryImpl
import com.example.yourdigitalpath.data.repositoryImp.OrderTrackRepositoryImp
import com.example.yourdigitalpath.data.repositoryImp.PreferencesRepositoryImpl
import com.example.yourdigitalpath.data.repositoryImp.ProfileRepositoryImpl
import com.example.yourdigitalpath.data.repositoryImp.ServiceRequestRepoImpl
import com.example.yourdigitalpath.domain.repository.OrderStatusRepository
import com.example.yourdigitalpath.domain.repository.OrderTrackRepository
import com.example.yourdigitalpath.domain.repository.PreferencesRepository
import com.example.yourdigitalpath.domain.repository.ProfileRepository
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
    abstract fun bindOrderStatusRepository(
        orderStatusRepositoryImpl: OrderStatusRepositoryImpl
    ): OrderStatusRepository

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        profileRepositoryImpl: ProfileRepositoryImpl
    ): ProfileRepository

    @Binds
    @Singleton
    abstract fun bindServiceRequestRepository(
        serviceRequestRepoImpl: ServiceRequestRepoImpl
    ): ServiceRequestRepository

    @Binds
    @Singleton
    abstract fun bindPreferencesRepository(
        preferencesRepositoryImpl: PreferencesRepositoryImpl
    ): PreferencesRepository

    @Binds
    @Singleton
    abstract fun bindOrderTrackRepository(
        orderTrackRepositoryImp: OrderTrackRepositoryImp
    ): OrderTrackRepository
}
