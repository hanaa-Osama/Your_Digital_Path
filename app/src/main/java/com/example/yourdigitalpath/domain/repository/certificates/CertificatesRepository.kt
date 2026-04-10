package com.example.yourdigitalpath.domain.repository.certificates

import com.example.yourdigitalpath.domain.model.certificates.CertificatesForm

interface CertificatesRepository {
    suspend fun saveBirthCertificate(form: CertificatesForm)
    suspend fun getCachedBirthCertificate(): CertificatesForm?
    suspend fun cacheBirthCertificate(form: CertificatesForm)
}