package com.example.yourdigitalpath.presentation.service_request

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.domain.model.ServiceRequestModel
import com.example.yourdigitalpath.domain.usecase.SaveServiceRequestUseCase
import com.example.yourdigitalpath.ui.theme.AppStrings
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceRequestViewModel @Inject constructor(
    private val saveServiceRequestUseCase: SaveServiceRequestUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(ServiceRequestModel())
    val uiState: StateFlow<ServiceRequestModel> = _uiState.asStateFlow()

    private val _isUploading = MutableStateFlow(false)
    val isUploading: StateFlow<Boolean> = _isUploading.asStateFlow()

    fun getServiceConfig(serviceName: String): ServiceConfig? {
        val type = getServiceType(serviceName)
        return ServiceConfigs.configs[type]
    }

    val isStep2Valid: StateFlow<Boolean> = _uiState.map { state ->
        val config = getServiceConfig(state.serviceName) ?: return@map false
        config.dataFields.all { field ->
            if (field.isRequired(state.selectedType)) {
                val value = state.dataValues[field.id] ?: ""
                value.isNotEmpty() && state.dataErrors[field.id] == null
            } else true
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val isAllRequiredFilesUploaded: StateFlow<Boolean> = _uiState.map { state ->
        val serviceType = getServiceType(state.serviceName)
        val selectedType = state.selectedType
        val files = state.fileUrls

        val hasNationalId = (files["national_id"] ?: emptyList()).size >= 2
        val hasServiceDoc = !(files["service_doc"] ?: emptyList()).isEmpty()
        val hasPersonalPhoto = !(files["personal_photo"] ?: emptyList()).isEmpty()
        val hasPoliceReport = !(files["police_report"] ?: emptyList()).isEmpty()
        val isLost = selectedType == AppStrings.LOST_REPLACEMENT

        when (serviceType) {
            ServiceTypes.NATIONAL_ID -> {
                val needsOldId = selectedType == AppStrings.RENEWAL ||
                        selectedType == AppStrings.DAMAGED_REPLACEMENT
                val needsBirthCert = selectedType == AppStrings.ISSUANCE
                hasPersonalPhoto &&
                        (if (needsOldId) hasNationalId else true) &&
                        (if (needsBirthCert) hasServiceDoc else true) &&
                        (if (isLost) hasPoliceReport else true)
            }

            ServiceTypes.DEATH_CERTIFICATE -> {
                hasNationalId && (if (isLost) hasPoliceReport else hasServiceDoc)
            }

            else -> {
                hasNationalId &&
                        (if (isLost) hasPoliceReport else hasServiceDoc) &&
                        (if (isLost) true else hasServiceDoc)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun getServiceType(serviceName: String): ServiceTypes = when {
        serviceName.contains("ميلاد") || serviceName.contains("birth") ||
                serviceName == AppStrings.BIRTH_CERTIFICATE -> ServiceTypes.BIRTH_CERTIFICATE

        serviceName.contains("بطاقة") || serviceName.contains("الهوية") ||
                serviceName.contains("id") || serviceName.contains("national") ||
                serviceName == AppStrings.NATIONAL_ID -> ServiceTypes.NATIONAL_ID

        serviceName.contains("زواج") || serviceName.contains("marriage") ||
                serviceName == AppStrings.MARRIAGE_CERTIFICATE -> ServiceTypes.MARRIAGE_CERTIFICATE

        serviceName.contains("وفاة") || serviceName.contains("death") ||
                serviceName == AppStrings.DEATH_CERTIFICATE -> ServiceTypes.DEATH_CERTIFICATE

        serviceName.contains("طلاق") || serviceName.contains("divorce") ||
                serviceName == AppStrings.DIVORCE_CERTIFICATE -> ServiceTypes.DIVORCE_CERTIFICATE
        else -> ServiceTypes.BIRTH_CERTIFICATE
    }

    fun calculateFees(state: ServiceRequestModel) {
        val config = getServiceConfig(state.serviceName) ?: return
        val total = config.basePrice * state.copiesCount
        _uiState.update { it.copy(totalFees = total) }
    }

    fun getRequestTypes(serviceName: String): List<String> =
        getServiceConfig(serviceName)?.availableTypes ?: emptyList()

    fun getRequestReasons(serviceName: String): List<String> = when (getServiceType(serviceName)) {
        ServiceTypes.NATIONAL_ID -> listOf(
            AppStrings.REASON_EXPIRY,
            AppStrings.REASON_LOST,
            AppStrings.REASON_DAMAGED,
            AppStrings.REASON_CHANGE_DATA
        )

        ServiceTypes.DEATH_CERTIFICATE -> listOf(
            AppStrings.REASON_INHERITANCE,
            AppStrings.REASON_INSURANCE,
            AppStrings.REASON_EMBASSY,
            AppStrings.REASON_LEGAL
        )

        ServiceTypes.DIVORCE_CERTIFICATE -> listOf(
            AppStrings.REASON_TRAVEL,
            AppStrings.REASON_REMARRIAGE,
            AppStrings.REASON_RESIDENCY,
            AppStrings.REASON_LEGAL
        )

        ServiceTypes.MARRIAGE_CERTIFICATE -> listOf(
            AppStrings.REASON_TRAVEL,
            AppStrings.REASON_WORK,
            AppStrings.REASON_EMBASSY,
            AppStrings.REASON_LEGAL
        )

        else -> listOf(
            AppStrings.REASON_RENEWAL,
            AppStrings.REASON_TRAVEL,
            AppStrings.REASON_WORK
        )
    }

    fun getDeliveryOptions(serviceName: String): List<String> = when (getServiceType(serviceName)) {
        ServiceTypes.NATIONAL_ID -> listOf(
            AppStrings.OFFICE_PICKUP,
            AppStrings.DELIVERY
        )

        else -> listOf(
            AppStrings.OFFICE_PICKUP,
            AppStrings.DELIVERY,
            AppStrings.DIGITAL
        )
    }

    fun hasCopiesAndDelivery(serviceName: String): Boolean =
        getServiceType(serviceName) != ServiceTypes.NATIONAL_ID

    fun updateDataValue(fieldId: String, value: String, validation: ValidationType) {
        val error = when (validation) {
            ValidationType.ARABIC_NAME -> {
                if (value.isNotEmpty() && value.trim()
                        .split(" ").size < 4
                ) context.getString(R.string.error_name_four_parts) else null
            }

            ValidationType.NATIONAL_ID -> {
                if (value.isNotEmpty() && (value.length != 14 || !value.all { it.isDigit() })) context.getString(
                    R.string.error_national_id_14
                ) else null
            }

            ValidationType.PHONE -> {
                if (value.isNotEmpty() && (
                            value.length != 11 ||
                                    !value.all { it.isDigit() } ||
                                    !value.startsWith("01")
                            )
                ) context.getString(R.string.error_phone_11) else null
            }

            else -> null
        }

        _uiState.update {
            it.copy(
                dataValues = it.dataValues + (fieldId to value),
                dataErrors = it.dataErrors + (fieldId to error)
            )
        }
    }

    fun uploadFile(fileId: String, uri: Uri, maxCount: Int) {
        val currentUrls = _uiState.value.fileUrls[fileId] ?: emptyList()
        if (currentUrls.size >= maxCount) return

        viewModelScope.launch {
            _isUploading.value = true
            try {
                _uiState.update {
                    it.copy(
                        fileUrls = it.fileUrls + (fileId to (currentUrls + uri.toString()))
                    )
                }
            } finally {
                _isUploading.value = false
            }
        }
    }

    fun removeFile(fileId: String, url: String) {
        val currentUrls = _uiState.value.fileUrls[fileId] ?: emptyList()
        _uiState.update {
            it.copy(
                fileUrls = it.fileUrls + (fileId to currentUrls.filter { u -> u != url })
            )
        }
    }

    fun calculateInitialFees(serviceName: String) {
        _uiState.update { it.copy(serviceName = serviceName) }
        calculateFees(_uiState.value)
    }

    fun updateSelectedType(type: String, serviceName: String) {
        _uiState.update { it.copy(selectedType = type) }
        calculateFees(_uiState.value)
    }

    fun updateCopiesCount(count: Int, serviceName: String) {
        _uiState.update { it.copy(copiesCount = count) }
        calculateFees(_uiState.value)
    }

    fun getBasePrice(serviceName: String): Double {
        return getServiceConfig(serviceName)?.basePrice ?: 0.0
    }

    fun updateRequestReason(reason: String) {
        _uiState.update { it.copy(requestReason = reason) }
    }

    fun updateOtherReason(reason: String) {
        _uiState.update { it.copy(otherReason = reason) }
    }

    fun updateDeliveryMethod(method: String) {
        _uiState.update { it.copy(deliveryMethod = method) }
    }

    fun updateServiceName(name: String) {
        _uiState.update { it.copy(serviceName = name) }
    }

    fun saveServiceRequest(onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val orderId = saveServiceRequestUseCase(_uiState.value)
                onSuccess(orderId)
            } catch (e: Exception) {
            }
        }
    }
}
