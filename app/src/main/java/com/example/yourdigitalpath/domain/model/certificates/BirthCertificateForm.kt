package com.example.yourdigitalpath.domain.model.certificates

data class BirthCertificateForm(
    val fullName: String = "",
    val dateOfBirth: String = "",
    val governorate: String = "",
    val applicantNationalId: String = "",
    val applicantPhone: String = "",
    val relationship: String = "" // "صاحب الوثيقة", "ولي الأمر", "وكيل"
)