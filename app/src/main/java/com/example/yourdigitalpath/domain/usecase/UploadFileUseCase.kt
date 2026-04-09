package com.example.yourdigitalpath.domain.usecase


import android.net.Uri
import com.example.yourdigitalpath.domain.repository.UploadRepository
import javax.inject.Inject

class UploadFileUseCase @Inject constructor(
    private val repository: UploadRepository
) {
    suspend operator fun invoke(uris: List<Uri>): Result<List<String>> {
        return repository.uploadFiles(uris)
    }
}