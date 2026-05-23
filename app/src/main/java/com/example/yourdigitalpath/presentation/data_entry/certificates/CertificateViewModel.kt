package com.example.yourdigitalpath.presentation.data_entry.certificates

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.domain.model.certificates.CertificatesForm
import com.example.yourdigitalpath.domain.usecase.certificates.CacheCertificatesUseCase
import com.example.yourdigitalpath.domain.usecase.certificates.GetCachedCertificatesUseCase
import com.example.yourdigitalpath.domain.usecase.certificates.SaveCertificatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
    @ApplicationContext private val context: Context,
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
        if (value.length <= 14 && value.all { it.isDigit() }) {
            _uiState.update {
                it.copy(
                    applicantNationalId = value,
                    applicantNationalIdError = null
                )
            }
            autoCache()
        }
    }

    fun updatePhone(value: String) {
        if (value.length <= 11 && value.all { it.isDigit() }) {
            _uiState.update { it.copy(applicantPhone = value, applicantPhoneError = null) }
            autoCache()
        }
    }

    fun updateRelationship(value: String) {
        _uiState.update { it.copy(relationship = value, relationshipError = null) }
        autoCache()
    }

    private fun autoCache() {
        val currentState = _uiState.value
        val form = CertificatesForm(
            fullName = currentState.fullName,
            dateOfBirth = currentState.dateOfBirth,
            governorate = currentState.governorate,
            applicantNationalId = currentState.applicantNationalId,
            applicantPhone = currentState.applicantPhone,
            relationship = currentState.relationship
        )
        viewModelScope.launch {
            cacheCertificatesUseCase(form)
        }
    }

    private fun validate(): Boolean {
        var isValid = true

        if (_uiState.value.fullName.trim().split(" ").size < 4) {
            _uiState.update { it.copy(fullNameError = context.getString(R.string.full_name_must_be_four)) }
            isValid = false
        }

        if (_uiState.value.applicantNationalId.length != 14) {
            _uiState.update { it.copy(applicantNationalIdError = context.getString(R.string.national_id_invalid)) }
            isValid = false
        }

        if (_uiState.value.applicantPhone.length != 11 || !_uiState.value.applicantPhone.startsWith(
                "01"
            )
        ) {
            _uiState.update { it.copy(applicantPhoneError = context.getString(R.string.phone_invalid)) }
            isValid = false
        }

        if (_uiState.value.dateOfBirth.isBlank()) {
            _uiState.update { it.copy(dateOfBirthError = context.getString(R.string.enter_birth_date)) }
            isValid = false
        }

        if (_uiState.value.governorate.isBlank()) {
            _uiState.update { it.copy(governorateError = context.getString(R.string.choose_governorate_error)) }
            isValid = false
        }

        if (_uiState.value.relationship.isBlank()) {
            _uiState.update { it.copy(relationshipError = context.getString(R.string.choose_relationship)) }
            isValid = false
        }

        return isValid
    }

    fun submitForm(onSuccess: () -> Unit) {
        if (validate()) {
            onSuccess()
        }
    }
}