package com.example.yourdigitalpath.data.dataSource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.yourdigitalpath.data.local.Dao.OrderDao
import com.example.yourdigitalpath.data.local.Dao.UserProfileDao
import com.example.yourdigitalpath.data.local.Dao.BirthCertificateDao
import com.example.yourdigitalpath.data.local.entity.UserProfileEntity
import com.example.yourdigitalpath.data.local.entity.OrderEntity
import com.example.yourdigitalpath.data.local.entity.BirthCertificateEntity

@Database(entities =
    [
    UserProfileEntity::class, OrderEntity::class, BirthCertificateEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun orderDao(): OrderDao
    abstract fun birthCertificateDao(): BirthCertificateDao

}