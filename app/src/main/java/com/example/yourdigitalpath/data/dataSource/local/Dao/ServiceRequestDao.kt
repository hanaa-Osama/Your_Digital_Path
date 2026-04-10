package com.example.yourdigitalpath.data.dataSource.local.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yourdigitalpath.data.dataSource.local.Entity.ServiceRequestEntity

@Dao
interface ServiceRequestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveServiceRequest(serviceRequest: ServiceRequestEntity)

    @Query("SELECT * FROM last_service_request WHERE id = 0")
    suspend fun getLastServiceRequest(): ServiceRequestEntity?
}
