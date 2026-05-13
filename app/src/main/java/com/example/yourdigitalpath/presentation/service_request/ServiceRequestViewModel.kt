package com.example.yourdigitalpath.presentation.service_request

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourdigitalpath.domain.model.ServiceRequestModel
import com.example.yourdigitalpath.domain.usecase.SaveServiceRequestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceRequestViewModel @Inject constructor(
    private val saveServiceRequestUseCase: SaveServiceRequestUseCase
) : ViewModel() {

    private val servicePrices = mapOf(
        "شهادة الميلاد|نسخة كاملة" to 20,
        "شهادة الميلاد|نسخة مختصرة" to 20,
        "شهادة الميلاد|رقمية موثقة" to 20,
        "شهادة الميلاد|بدل فاقد" to 20,
        "بطاقة الهوية|default" to 35,
        "شهادة الزواج|default" to 30,
        "شهادة الزواج|موثقة للسفارة" to 30,
        "شهادة الزواج|بدل فاقد" to 30,
        "شهادة الوفاة|default" to 20,
        "شهادة الطلاق|طلاق قضائي" to 30,
        "شهادة الطلاق|طلاق أمام ماذون" to 30,
        "شهادة الطلاق|خلع" to 30
    )

    fun getServiceType(serviceName: String): ServiceTypes = when {
        serviceName.contains("ميلاد") -> ServiceTypes.BIRTH_CERTIFICATE
        serviceName.contains("بطاقة") || serviceName.contains("الهوية") -> ServiceTypes.NATIONAL_ID
        serviceName.contains("زواج") -> ServiceTypes.MARRIAGE_CERTIFICATE
        serviceName.contains("وفاة") -> ServiceTypes.DEATH_CERTIFICATE
        serviceName.contains("طلاق") -> ServiceTypes.DIVORCE_CERTIFICATE
        else -> ServiceTypes.BIRTH_CERTIFICATE
    }

    private fun getNormalizedServiceName(serviceName: String): String = when {
        serviceName.contains("ميلاد") -> "شهادة الميلاد"
        serviceName.contains("بطاقة") || serviceName.contains("الهوية") -> "بطاقة الهوية"
        serviceName.contains("زواج") -> "شهادة الزواج"
        serviceName.contains("وفاة") -> "شهادة الوفاة"
        serviceName.contains("طلاق") -> "شهادة الطلاق"
        else -> serviceName
    }

    private fun calculateFees(state: ServiceRequestModel, serviceName: String): Double {
        val normalized = getNormalizedServiceName(serviceName)
        val serviceType = getServiceType(serviceName)
        val useDefault =
            serviceType == ServiceTypes.NATIONAL_ID || serviceType == ServiceTypes.DEATH_CERTIFICATE
        val priceKey =
            if (useDefault || state.selectedType.isEmpty()) "$normalized|default" else "$normalized|${state.selectedType}"
        val basePrice = servicePrices[priceKey] ?: servicePrices["$normalized|default"] ?: 0
        val copies = if (hasCopiesAndDelivery(serviceName)) state.copiesCount else 1
        return (basePrice * copies).toDouble()
    }

    fun getRequestTypes(serviceName: String): List<String> = when (getServiceType(serviceName)) {
        ServiceTypes.BIRTH_CERTIFICATE -> listOf(
            "نسخة كاملة",
            "نسخة مختصرة",
            "رقمية موثقة",
            "بدل فاقد"
        )

        ServiceTypes.NATIONAL_ID -> listOf("إصدار لأول مرة", "تجديد", "بدل فاقد", "بدل تالف")
        ServiceTypes.MARRIAGE_CERTIFICATE -> listOf(
            "نسخة كاملة",
            "نسخة مختصرة",
            "موثقة للسفارة",
            "بدل فاقد"
        )

        ServiceTypes.DEATH_CERTIFICATE -> listOf("إصدار لأول مرة", "نسخة إضافية", "بدل فاقد")
        ServiceTypes.DIVORCE_CERTIFICATE -> listOf("طلاق قضائي", "طلاق أمام ماذون", "خلع")
    }

    fun getRequestReasons(serviceName: String): List<String> = when (getServiceType(serviceName)) {
        ServiceTypes.NATIONAL_ID -> listOf("انتهاء الصلاحية", "فقدان", "تلف", "تغيير بيانات")
        ServiceTypes.DEATH_CERTIFICATE -> listOf("ميراث", "تأمين", "سفارة", "إجراءات قانونية")
        ServiceTypes.DIVORCE_CERTIFICATE -> listOf("سفر", "زواج مرة أخرى", "إقامة", "قانوني")
        else -> listOf("تجديد", "سفر", "عمل")
    }

    fun getDeliveryOptions(serviceName: String): List<String> = when (getServiceType(serviceName)) {
        ServiceTypes.NATIONAL_ID -> listOf("في المكتب", "توصيل")
        else -> listOf("في المكتب", "توصيل", "رقمي")
    }

    fun hasCopiesAndDelivery(serviceName: String): Boolean =
        getServiceType(serviceName) != ServiceTypes.NATIONAL_ID

    private val _uiState = MutableStateFlow(ServiceRequestModel())
    val uiState: StateFlow<ServiceRequestModel> = _uiState.asStateFlow()

    private val _isUploading = MutableStateFlow(false)
    val isUploading: StateFlow<Boolean> = _isUploading.asStateFlow()

    fun updateNationalIdNumber(id: String) {
        val error = when {
            id.isEmpty() -> null
            id.length != 14 -> "يجب أن يتكون الرقم القومي من 14 رقمًا"
            !id.all { it.isDigit() } -> "يرجى إدخال أرقام فقط"
            else -> null
        }
        _uiState.update { it.copy(nationalIdNumber = id, nationalIdError = error) }
    }


    fun updatePhoneNumber(phone: String) {
        val error = when {
            phone.isEmpty() -> null
            phone.length != 11 -> "يجب أن يتكون رقم الهاتف من 11 رقمًا"
            !phone.all { it.isDigit() } -> "يرجى إدخال أرقام فقط"
            else -> null
        }
        _uiState.update { it.copy(phoneNumber = phone, phoneError = error) }
    }

    fun calculateInitialFees(serviceName: String) {
        _uiState.update { it.copy(totalFees = calculateFees(it, serviceName)) }
    }

    fun updateSelectedType(type: String, serviceName: String) {
        _uiState.update {
            val s = it.copy(selectedType = type)
            s.copy(totalFees = calculateFees(s, serviceName))
        }
    }

    fun updateCopiesCount(count: Int, serviceName: String) {
        _uiState.update {
            val s = it.copy(copiesCount = count)
            s.copy(totalFees = calculateFees(s, serviceName))
        }
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

    fun uploadNationalId(uri: Uri) {
        if (_uiState.value.nationalIdUrls.size >= 2) return
        viewModelScope.launch {
            _isUploading.value = true
            try {
                _uiState.update { it.copy(nationalIdUrls = it.nationalIdUrls + uri.toString()) }
            } finally {
                _isUploading.value = false
            }
        }
    }

    fun removeNationalId(url: String) {
        _uiState.update { it.copy(nationalIdUrls = it.nationalIdUrls.filter { u -> u != url }) }
    }

    fun uploadServiceDocument(uri: Uri) {
        viewModelScope.launch {
            _isUploading.value = true
            try {
                _uiState.update { it.copy(serviceDocumentUrl = uri.toString()) }
            } finally {
                _isUploading.value = false
            }
        }
    }

    fun removeServiceDocument() {
        _uiState.update { it.copy(serviceDocumentUrl = null) }
    }

    fun uploadPersonalPhoto(uri: Uri) {
        viewModelScope.launch {
            _isUploading.value = true
            try {
                _uiState.update { it.copy(personalPhotoUrl = uri.toString()) }
            } finally {
                _isUploading.value = false
            }
        }
    }

    fun removePersonalPhoto() {
        _uiState.update { it.copy(personalPhotoUrl = null) }
    }

    fun uploadPoliceReport(uri: Uri) {
        viewModelScope.launch {
            _isUploading.value = true
            try {
                _uiState.update { it.copy(policeReportUrl = uri.toString()) }
            } finally {
                _isUploading.value = false
            }
        }
    }

    fun removePoliceReport() {
        _uiState.update { it.copy(policeReportUrl = null) }
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

    fun isAllRequiredFilesUploaded(serviceName: String): Boolean {
        val state = _uiState.value
        val serviceType = getServiceType(serviceName)

        return when (serviceType) {
            ServiceTypes.NATIONAL_ID -> {

                val hasIdImages = state.nationalIdUrls.size >= 2
                val hasPersonalPhoto = !state.personalPhotoUrl.isNullOrEmpty()
                val needsPoliceReport = state.selectedType == "بدل فاقد"
                val hasPoliceReport =
                    if (needsPoliceReport) !state.policeReportUrl.isNullOrEmpty() else true

                hasIdImages && hasPersonalPhoto && hasPoliceReport
            }

            else -> {

                val hasIdImages = state.nationalIdUrls.size >= 2
                val hasServiceDoc = !state.serviceDocumentUrl.isNullOrEmpty()

                hasIdImages && hasServiceDoc
            }
        }
    }
}