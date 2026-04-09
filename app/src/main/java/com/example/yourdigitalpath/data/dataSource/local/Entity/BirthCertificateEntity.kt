package com.example.yourdigitalpath.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "birth_certificate_cache")
data class BirthCertificateEntity(
    @PrimaryKey val id: Int = 0, // Single row for cache
    val fullName: String,
    val dateOfBirth: String,
    val governorate: String,
    val applicantNationalId: String,
    val applicantPhone: String,
    val relationship: String
)
