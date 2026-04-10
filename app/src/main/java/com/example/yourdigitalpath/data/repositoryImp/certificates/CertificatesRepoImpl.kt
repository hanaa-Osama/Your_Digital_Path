package com.example.yourdigitalpath.data.repositoryImp.certificates

import com.example.yourdigitalpath.data.dataSource.local.Dao.certificates.CertificatesDao
import com.example.yourdigitalpath.data.dataSource.local.Entity.certificates.CertificatesEntity
import com.example.yourdigitalpath.domain.model.certificates.CertificatesForm
import com.example.yourdigitalpath.domain.repository.certificates.CertificatesRepository
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CertificatesRepoImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val certificatesDao: CertificatesDao
) : CertificatesRepository {

    override suspend fun saveBirthCertificate(form: CertificatesForm) {
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
        certificatesDao.clearCache()
    }

    override suspend fun getCachedBirthCertificate(): CertificatesForm? {
        return certificatesDao.getCachedForm()?.let {
            CertificatesForm(
                fullName = it.fullName,
                dateOfBirth = it.dateOfBirth,
                governorate = it.governorate,
                applicantNationalId = it.applicantNationalId,
                applicantPhone = it.applicantPhone,
                relationship = it.relationship
            )
        }
    }

    override suspend fun cacheBirthCertificate(form: CertificatesForm) {
        certificatesDao.cacheForm(
            CertificatesEntity(
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