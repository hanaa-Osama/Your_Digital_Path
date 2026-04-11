package com.example.yourdigitalpath.di

import com.example.yourdigitalpath.data.repositoryImp.OrderTrackRepositoryImp
import com.example.yourdigitalpath.data.repositoryImp.PreferencesRepositoryImpl
import com.example.yourdigitalpath.data.repositoryImp.ProfileRepositoryImpl
import com.example.yourdigitalpath.data.repositoryImp.ServiceRequestRepoImpl
import com.example.yourdigitalpath.data.repositoryImp.certificates.CertificatesRepoImpl
import com.example.yourdigitalpath.domain.repository.OrderStatusRepository
import com.example.yourdigitalpath.domain.repository.OrderTrackRepository
import com.example.yourdigitalpath.domain.repository.PreferencesRepository
import com.example.yourdigitalpath.domain.repository.ProfileRepository
import com.example.yourdigitalpath.domain.repository.ServiceRequestRepository
import com.example.yourdigitalpath.domain.repository.certificates.CertificatesRepository
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


    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        impl: ProfileRepositoryImpl
    ): ProfileRepository

    @Binds
    @Singleton
    abstract fun bindOrderRepository(
        impl: OrderRepositoryImpl
    ): OrderRepository

    @Binds
    @Singleton
    abstract fun bindPreferencesRepository(
        impl: PreferencesRepositoryImpl
    ): PreferencesRepository

    @Binds
    @Singleton
    abstract fun bindBirthCertificateRepository(
        impl: CertificatesRepoImpl
    ): CertificatesRepository

    @Binds
    @Singleton
    abstract fun bindOrderTrackRepository(
        impl: OrderTrackRepositoryImp
    ): OrderTrackRepository
}
