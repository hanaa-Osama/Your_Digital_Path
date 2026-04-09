package com.example.yourdigitalpath.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.yourdigitalpath.data.dataSource.local.Database
import com.example.yourdigitalpath.data.local.Dao.OrderDao
import com.example.yourdigitalpath.data.local.Dao.UserProfileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): Database =
        Room.databaseBuilder(context, Database::class.java, "app_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideUserProfileDao(database: Database): UserProfileDao =
        database.userProfileDao()

    @Provides
    fun provideOrderDao(database: Database): OrderDao =
        database.orderDao()


    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
}
