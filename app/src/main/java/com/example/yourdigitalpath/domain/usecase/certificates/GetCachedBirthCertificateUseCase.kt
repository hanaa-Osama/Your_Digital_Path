package com.example.yourdigitalpath.domain.usecase.certificates

import com.example.yourdigitalpath.domain.model.certificates.BirthCertificateForm
import com.example.yourdigitalpath.domain.repository.certificates.BirthCertificateRepository
import javax.inject.Inject

class GetCachedBirthCertificateUseCase @Inject constructor(
    private val repository: BirthCertificateRepository
) {
    suspend operator fun invoke(): BirthCertificateForm? {
        return repository.getCachedBirthCertificate()
    }
}