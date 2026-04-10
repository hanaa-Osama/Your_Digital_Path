package com.example.yourdigitalpath.domain.usecase.certificates

import com.example.yourdigitalpath.domain.model.certificates.BirthCertificateForm
import com.example.yourdigitalpath.domain.repository.certificates.BirthCertificateRepository
import javax.inject.Inject

class CacheBirthCertificateUseCase @Inject constructor(
    private val repository: BirthCertificateRepository
) {
    suspend operator fun invoke(form: BirthCertificateForm) {
        repository.cacheBirthCertificate(form)
    }
}
