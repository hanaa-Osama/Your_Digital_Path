package com.example.yourdigitalpath.data.dataSource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.yourdigitalpath.data.dataSource.local.Dao.ServiceRequestDao
import com.example.yourdigitalpath.data.dataSource.local.Dao.certificates.BirthCertificateDao
import com.example.yourdigitalpath.data.dataSource.local.Entity.ServiceRequestEntity
import com.example.yourdigitalpath.data.dataSource.local.Entity.certificates.BirthCertificateEntity
import com.example.yourdigitalpath.data.dataSource.local.Dao.NotificationDao
import com.example.yourdigitalpath.data.dataSource.local.Entity.NotificationEntity
import com.example.yourdigitalpath.data.local.Dao.OrderDao
import com.example.yourdigitalpath.data.local.Dao.UserProfileDao
import com.example.yourdigitalpath.data.local.entity.UserProfileEntity
import com.example.yourdigitalpath.data.local.entity.OrderEntity

@Database(entities =
    [
        UserProfileEntity::class,
        OrderEntity::class,
        NotificationEntity::class,
        UserProfileEntity::class, OrderEntity::class, BirthCertificateEntity::class, ServiceRequestEntity::class
    ],
    version = 7,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun orderDao(): OrderDao
    abstract fun notificationDao(): NotificationDao
    abstract fun birthCertificateDao(): BirthCertificateDao
    abstract fun serviceRequestDao(): ServiceRequestDao

}