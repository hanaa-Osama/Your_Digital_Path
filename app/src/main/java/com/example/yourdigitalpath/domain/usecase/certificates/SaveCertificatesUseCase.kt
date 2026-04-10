package com.example.yourdigitalpath.domain.usecase.certificates

import com.example.yourdigitalpath.domain.model.certificates.CertificatesForm
import com.example.yourdigitalpath.domain.repository.certificates.CertificatesRepository
import javax.inject.Inject

class SaveCertificatesUseCase @Inject constructor(
    private val repository: CertificatesRepository
) {
    suspend operator fun invoke(form: CertificatesForm) {
        repository.saveBirthCertificate(form)
    }
}