package com.example.yourdigitalpath.presentation.data_entry.certificates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourdigitalpath.domain.model.certificates.CertificatesForm
import com.example.yourdigitalpath.domain.usecase.certificates.CacheCertificatesUseCase
import com.example.yourdigitalpath.domain.usecase.certificates.GetCachedCertificatesUseCase
import com.example.yourdigitalpath.domain.usecase.certificates.SaveCertificatesUseCase
import com.example.yourdigitalpath.utils.Validator // تأكدي من هذا الـ import
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BirthCertificateUiState(
    val fullName: String = "",
    val fullNameError: String? = null,
    val dateOfBirth: String = "",
    val dateOfBirthError: String? = null,
    val governorate: String = "",
    val governorateError: String? = null,
    val applicantNationalId: String = "",
    val applicantNationalIdError: String? = null,
    val applicantPhone: String = "",
    val applicantPhoneError: String? = null,
    val relationship: String = "",
    val relationshipError: String? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class BirthCertificateViewModel @Inject constructor(
    private val saveCertificatesUseCase: SaveCertificatesUseCase,
    private val getCachedCertificatesUseCase: GetCachedCertificatesUseCase,
    private val cacheCertificatesUseCase: CacheCertificatesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BirthCertificateUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadCachedData()
    }

    private fun loadCachedData() {
        viewModelScope.launch {
            getCachedCertificatesUseCase()?.let { cached ->
                _uiState.update {
                    it.copy(
                        fullName = cached.fullName,
                        dateOfBirth = cached.dateOfBirth,
                        governorate = cached.governorate,
                        applicantNationalId = cached.applicantNationalId,
                        applicantPhone = cached.applicantPhone,
                        relationship = cached.relationship
                    )
                }
            }
        }
    }

    fun updateFullName(value: String) {
        _uiState.update { it.copy(fullName = value, fullNameError = null) }
        autoCache()
    }

    fun updateDateOfBirth(value: String) {
        _uiState.update { it.copy(dateOfBirth = value, dateOfBirthError = null) }
        autoCache()
    }

    fun updateGovernorate(value: String) {
        _uiState.update { it.copy(governorate = value, governorateError = null) }
        autoCache()
    }

    fun updateNationalId(value: String) {
        if (value.length <= 14) {
            val error = Validator.validateNationalId(value)
            _uiState.update {
                it.copy(
                    applicantNationalId = value,
                    applicantNationalIdError = error
                )
            }
            autoCache()
        }
    }

    fun updatePhone(value: String) {
        if (value.length <= 11) {
            val error = Validator.validatePhone(value)
            _uiState.update { it.copy(applicantPhone = value, applicantPhoneError = error) }
            autoCache()
        }
    }

    fun updateRelationship(value: String) {
        _uiState.update { it.copy(relationship = value, relationshipError = null) }
        autoCache()
    }

    private fun autoCache() {
        val currentState = _uiState.value
        viewModelScope.launch {
            cacheCertificatesUseCase(
                CertificatesForm(
                    fullName = currentState.fullName,
                    dateOfBirth = currentState.dateOfBirth,
                    governorate = currentState.governorate,
                    applicantNationalId = currentState.applicantNationalId,
                    applicantPhone = currentState.applicantPhone,
                    relationship = currentState.relationship
                )
            )
        }
    }

    private fun validate(): Boolean {
        var isValid = true
        val currentState = _uiState.value

        if (currentState.fullName.trim().split(" ").size < 4) {
            _uiState.update { it.copy(fullNameError = "يجب إدخال الاسم رباعياً") }
            isValid = false
        }

        val idError = Validator.validateNationalId(currentState.applicantNationalId)
        val phoneError = Validator.validatePhone(currentState.applicantPhone)

        if (idError != null) {
            _uiState.update { it.copy(applicantNationalIdError = idError) }
            isValid = false
        }
        if (phoneError != null) {
            _uiState.update { it.copy(applicantPhoneError = phoneError) }
            isValid = false
        }

        if (currentState.dateOfBirth.isBlank()) {
            _uiState.update { it.copy(dateOfBirthError = "يرجى إدخال تاريخ الميلاد") }
            isValid = false
        }
        if (currentState.governorate.isBlank()) {
            _uiState.update { it.copy(governorateError = "يرجى اختيار المحافظة") }
            isValid = false
        }
        if (currentState.relationship.isBlank()) {
            _uiState.update { it.copy(relationshipError = "يرجى اختيار صلة القرابة") }
            isValid = false
        }

        return isValid
    }

    fun submitForm(onSuccess: () -> Unit) {
        if (validate()) onSuccess()
    }
}