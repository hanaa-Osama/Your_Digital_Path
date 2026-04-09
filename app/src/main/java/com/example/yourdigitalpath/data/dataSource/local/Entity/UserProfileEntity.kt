package com.example.yourdigitalpath.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val nationalId: String,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val governorate: String?
)
