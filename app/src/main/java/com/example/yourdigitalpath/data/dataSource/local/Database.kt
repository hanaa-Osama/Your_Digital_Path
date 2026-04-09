package com.example.yourdigitalpath.data.dataSource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.yourdigitalpath.data.dataSource.local.Dao.ServiceRequestDao
import com.example.yourdigitalpath.data.dataSource.local.Dao.certificates.BirthCertificateDao
import com.example.yourdigitalpath.data.dataSource.local.Entity.ServiceRequestEntity
import com.example.yourdigitalpath.data.dataSource.local.Entity.certificates.BirthCertificateEntity
import com.example.yourdigitalpath.data.local.Dao.OrderDao
import com.example.yourdigitalpath.data.local.Dao.UserProfileDao
import com.example.yourdigitalpath.data.local.entity.OrderEntity
import com.example.yourdigitalpath.data.local.entity.UserProfileEntity

@Database(entities =
    [
        UserProfileEntity::class, OrderEntity::class, BirthCertificateEntity::class, ServiceRequestEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun orderDao(): OrderDao
    abstract fun birthCertificateDao(): BirthCertificateDao
    abstract fun serviceRequestDao(): ServiceRequestDao

}