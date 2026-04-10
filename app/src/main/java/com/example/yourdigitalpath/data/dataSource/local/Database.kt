package com.example.yourdigitalpath.data.dataSource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.yourdigitalpath.data.dataSource.local.Dao.NotificationDao
import com.example.yourdigitalpath.data.dataSource.local.Dao.ServiceRequestDao
import com.example.yourdigitalpath.data.dataSource.local.Dao.certificates.CertificatesDao
import com.example.yourdigitalpath.data.dataSource.local.Entity.NotificationEntity
import com.example.yourdigitalpath.data.dataSource.local.Entity.ServiceRequestEntity
import com.example.yourdigitalpath.data.dataSource.local.Entity.certificates.CertificatesEntity
import com.example.yourdigitalpath.data.local.Dao.OrderDao
import com.example.yourdigitalpath.data.local.Dao.UserProfileDao
import com.example.yourdigitalpath.data.local.entity.OrderEntity
import com.example.yourdigitalpath.data.local.entity.UserProfileEntity

@Database(entities =
    [
        NotificationEntity::class,
        UserProfileEntity::class,
        OrderEntity::class,
        CertificatesEntity::class,
        ServiceRequestEntity::class
    ],
    version = 5,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun orderDao(): OrderDao
    abstract fun notificationDao(): NotificationDao
    abstract fun birthCertificateDao(): CertificatesDao
    abstract fun serviceRequestDao(): ServiceRequestDao

}