package com.example.yourdigitalpath.data.dataSource.local.Dao.certificates

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yourdigitalpath.data.dataSource.local.Entity.certificates.CertificatesEntity

@Dao
interface CertificatesDao {
    @Query("SELECT * FROM birth_certificate_cache WHERE id = 0")
    suspend fun getCachedForm(): CertificatesEntity?

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun cacheForm(form: CertificatesEntity)

    @Query("DELETE FROM birth_certificate_cache")
    suspend fun clearCache()
}