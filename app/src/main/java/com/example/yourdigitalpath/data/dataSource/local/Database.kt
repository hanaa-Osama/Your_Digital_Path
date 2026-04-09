package com.example.yourdigitalpath.data.dataSource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.yourdigitalpath.data.local.Dao.OrderDao
import com.example.yourdigitalpath.data.local.Dao.UserProfileDao
import com.example.yourdigitalpath.data.local.entity.OrderEntity
import com.example.yourdigitalpath.data.local.entity.UserProfileEntity

@Database(
    entities =
        [UserProfileEntity::class, OrderEntity::class, NotificationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun orderDao(): OrderDao
    abstract fun notificationDao(): NotificationDao

}