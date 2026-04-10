package com.example.yourdigitalpath.presentation.dataEntry.certificates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourdigitalpath.domain.model.certificates.BirthCertificateForm
import com.example.yourdigitalpath.domain.usecase.certificates.CacheBirthCertificateUseCase
import com.example.yourdigitalpath.domain.usecase.certificates.GetCachedBirthCertificateUseCase
import com.example.yourdigitalpath.domain.usecase.certificates.SaveBirthCertificateUseCase
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
    private val saveBirthCertificateUseCase: SaveBirthCertificateUseCase,
    private val getCachedBirthCertificateUseCase: GetCachedBirthCertificateUseCase,
    private val cacheBirthCertificateUseCase: CacheBirthCertificateUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BirthCertificateUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadCachedData()
    }

    private fun loadCachedData() {
        viewModelScope.launch {
            getCachedBirthCertificateUseCase()?.let { cached ->
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
        _uiState.update { it.copy(applicantNationalId = value, applicantNationalIdError = null) }
        autoCache()
    }

    fun updatePhone(value: String) {
        _uiState.update { it.copy(applicantPhone = value, applicantPhoneError = null) }
        autoCache()
    }

    fun updateRelationship(value: String) {
        _uiState.update { it.copy(relationship = value, relationshipError = null) }
        autoCache()
    }

    private fun autoCache() {
        val currentState = _uiState.value
        val form = BirthCertificateForm(
            fullName = currentState.fullName,
            dateOfBirth = currentState.dateOfBirth,
            governorate = currentState.governorate,
            applicantNationalId = currentState.applicantNationalId,
            applicantPhone = currentState.applicantPhone,
            relationship = currentState.relationship
        )
        viewModelScope.launch {
            cacheBirthCertificateUseCase(form)
        }
    }

    private fun validate(): Boolean {
        var isValid = true
        val currentState = _uiState.value

        if (currentState.fullName.isBlank()) {
            _uiState.update { it.copy(fullNameError = "يرجى إدخال الاسم الرباعي") }
            isValid = false
        } else if (currentState.fullName.trim().split(" ").size < 4) {
            _uiState.update { it.copy(fullNameError = "يجب إدخال الاسم رباعياً") }
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

        if (currentState.applicantNationalId.length != 14) {
            _uiState.update { it.copy(applicantNationalIdError = "الرقم القومي يجب أن يكون 14 رقم") }
            isValid = false
        }

        if (currentState.applicantPhone.length != 11 || !currentState.applicantPhone.startsWith("01")) {
            _uiState.update { it.copy(applicantPhoneError = "رقم الهاتف غير صحيح") }
            isValid = false
        }

        if (currentState.relationship.isBlank()) {
            _uiState.update { it.copy(relationshipError = "يرجى اختيار صلة القرابة") }
            isValid = false
        }

        return isValid
    }

    fun submitForm(onSuccess: () -> Unit) {
        if (validate()) {
            onSuccess()
        }
    }

    fun submitFinalForm(onSuccess: () -> Unit) {
        if (!validate()) return

        val currentState = _uiState.value
        val form = BirthCertificateForm(
            fullName = currentState.fullName,
            dateOfBirth = currentState.dateOfBirth,
            governorate = currentState.governorate,
            applicantNationalId = currentState.applicantNationalId,
            applicantPhone = currentState.applicantPhone,
            relationship = currentState.relationship
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                saveBirthCertificateUseCase(form)
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                onSuccess()
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}
