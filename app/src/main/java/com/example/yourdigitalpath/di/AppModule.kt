package com.example.yourdigitalpath.di

import android.content.Context
import androidx.room.Room
import com.example.yourdigitalpath.data.dataSource.local.NotificationDao
import com.example.yourdigitalpath.data.dataSource.local.NotificationDatabase
import com.example.yourdigitalpath.data.dataSource.remote.FirestoreNotificationListener
import com.example.yourdigitalpath.data.repositoryImp.NotificationRepositoryImpl
import com.example.yourdigitalpath.data.repositoryImp.TrackingRepositoryImpl
import com.example.yourdigitalpath.domain.repository.NotificationRepository
import com.example.yourdigitalpath.domain.repository.TrackingRepository
import com.example.yourdigitalpath.domain.usecase.GetNotificationsUseCase
import com.example.yourdigitalpath.domain.usecase.MarkNotificationAsReadUseCase
import com.example.yourdigitalpath.domain.usecase.ObserveOrderTrackingUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NotificationDatabase {
        return Room.databaseBuilder(
            context,
            NotificationDatabase::class.java,
            "notification_db"
        ).build()
    }


    @Provides
    fun provideNotificationDao(db: NotificationDatabase): NotificationDao {
        return db.notificationDao()
    }


    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }


    @Provides
    @Singleton
    fun provideNotificationRepository(
        dao: NotificationDao
    ): NotificationRepository {
        return NotificationRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideTrackingRepository(
        firestore: FirebaseFirestore
    ): TrackingRepository {
        return TrackingRepositoryImpl(firestore)
    }

    @Provides
    fun provideGetNotificationsUseCase(
        repo: NotificationRepository
    ): GetNotificationsUseCase {
        return GetNotificationsUseCase(repo)
    }

    @Provides
    fun provideMarkAsReadUseCase(
        repo: NotificationRepository
    ): MarkNotificationAsReadUseCase {
        return MarkNotificationAsReadUseCase(repo)
    }

    @Provides
    fun provideObserveTrackingUseCase(
        repo: TrackingRepository
    ): ObserveOrderTrackingUseCase {
        return ObserveOrderTrackingUseCase(repo)
    }

// ضيفي ده جوه الـ AppModule

    @Provides
    @Singleton
    fun provideFirestoreNotificationListener(
        firestore: FirebaseFirestore,
        dao: NotificationDao,
        @ApplicationContext context: Context
    ): FirestoreNotificationListener {
        return FirestoreNotificationListener(firestore, dao, context)
    }
}