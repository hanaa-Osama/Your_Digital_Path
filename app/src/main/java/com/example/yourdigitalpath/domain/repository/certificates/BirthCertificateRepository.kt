package com.example.yourdigitalpath.domain.repository.certificates

import com.example.yourdigitalpath.domain.model.certificates.BirthCertificateForm

interface BirthCertificateRepository {
    suspend fun saveBirthCertificate(form: BirthCertificateForm)
    suspend fun getCachedBirthCertificate(): BirthCertificateForm?
    suspend fun cacheBirthCertificate(form: BirthCertificateForm)
}