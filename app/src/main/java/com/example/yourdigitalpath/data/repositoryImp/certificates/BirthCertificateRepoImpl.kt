package com.example.yourdigitalpath.data.repositoryImp.certificates

import com.example.yourdigitalpath.data.dataSource.local.Dao.certificates.BirthCertificateDao
import com.example.yourdigitalpath.data.dataSource.local.Entity.certificates.BirthCertificateEntity
import com.example.yourdigitalpath.domain.model.certificates.BirthCertificateForm
import com.example.yourdigitalpath.domain.repository.certificates.BirthCertificateRepository
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BirthCertificateRepoImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val birthCertificateDao: BirthCertificateDao
) : BirthCertificateRepository {

    override suspend fun saveBirthCertificate(form: BirthCertificateForm) {
        // Save to Firebase
        val data = hashMapOf(
            "fullName" to form.fullName,
            "dateOfBirth" to form.dateOfBirth,
            "governorate" to form.governorate,
            "applicantNationalId" to form.applicantNationalId,
            "applicantPhone" to form.applicantPhone,
            "relationship" to form.relationship,
            "timestamp" to Timestamp.now()
        )
        firestore.collection("birth_certificates").add(data).await()

        // Clear local cache after successful remote save
        birthCertificateDao.clearCache()
    }

    override suspend fun getCachedBirthCertificate(): BirthCertificateForm? {
        return birthCertificateDao.getCachedForm()?.let {
            BirthCertificateForm(
                fullName = it.fullName,
                dateOfBirth = it.dateOfBirth,
                governorate = it.governorate,
                applicantNationalId = it.applicantNationalId,
                applicantPhone = it.applicantPhone,
                relationship = it.relationship
            )
        }
    }

    override suspend fun cacheBirthCertificate(form: BirthCertificateForm) {
        birthCertificateDao.cacheForm(
            BirthCertificateEntity(
                fullName = form.fullName,
                dateOfBirth = form.dateOfBirth,
                governorate = form.governorate,
                applicantNationalId = form.applicantNationalId,
                applicantPhone = form.applicantPhone,
                relationship = form.relationship
            )
        )
    }
}