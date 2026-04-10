package com.example.yourdigitalpath.domain.usecase.certificates

import com.example.yourdigitalpath.domain.model.certificates.BirthCertificateForm
import com.example.yourdigitalpath.domain.repository.certificates.BirthCertificateRepository
import javax.inject.Inject

class SaveBirthCertificateUseCase @Inject constructor(
    private val repository: BirthCertificateRepository
) {
    suspend operator fun invoke(form: BirthCertificateForm) {
        repository.saveBirthCertificate(form)
    }
}