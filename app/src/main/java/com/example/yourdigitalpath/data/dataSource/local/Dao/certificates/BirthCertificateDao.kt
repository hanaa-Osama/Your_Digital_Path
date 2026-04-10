package com.example.yourdigitalpath.data.dataSource.local.Dao.certificates

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yourdigitalpath.data.dataSource.local.Entity.certificates.BirthCertificateEntity

@Dao
interface BirthCertificateDao {
    @Query("SELECT * FROM birth_certificate_cache WHERE id = 0")
    suspend fun getCachedForm(): BirthCertificateEntity?

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun cacheForm(form: BirthCertificateEntity)

    @Query("DELETE FROM birth_certificate_cache")
    suspend fun clearCache()
}