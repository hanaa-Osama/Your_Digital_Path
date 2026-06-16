package com.example.yourdigitalpath.data.dataSource.local.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yourdigitalpath.data.dataSource.local.Entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notifications WHERE userId = :userId ORDER BY createdAt DESC")
    fun getNotificationsFlow(userId: String): Flow<List<NotificationEntity>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity)

    @Query("UPDATE notifications SET isRead = 1 WHERE id = :id")
    suspend fun markAsRead(id: String)

    @Query("DELETE FROM notifications WHERE userId = :userId")
    suspend fun clearAll(userId: String)

    @Query("DELETE FROM notifications WHERE id = :id")
    suspend fun deleteNotification(id: String)
}