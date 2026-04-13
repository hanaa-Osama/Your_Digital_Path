package com.example.yourdigitalpath.presentation.service_request

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourdigitalpath.domain.model.ServiceRequestModel
import com.example.yourdigitalpath.domain.usecase.SaveServiceRequestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val saveServiceRequestUseCase: SaveServiceRequestUseCase
) : ViewModel() {

    private val PRICE_PER_COPY = 20.0

    private val _uiState = MutableStateFlow(
        ServiceRequestModel(
            selectedType = "",
            requestReason = "",
            otherReason = "",
            deliveryMethod = "",
            copiesCount = 1, // عدد النسخ
            totalFees = 20.0 // سعر النسخة الواحدة
        )
    )

    val uiState: StateFlow<ServiceRequestModel> = _uiState.asStateFlow()

    private val _isUploading = MutableStateFlow(false)
    val isUploading: StateFlow<Boolean> = _isUploading.asStateFlow()

    val isAllRequiredFilesUploaded: StateFlow<Boolean> = _uiState.map { state ->
        state.nationalIdUrls.isNotEmpty() && state.serviceDocumentUrl != null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun uploadNationalId(uri: Uri) {
        if (_uiState.value.nationalIdUrls.size >= 2) return
        viewModelScope.launch {
            _isUploading.value = true
            try {
                val fileUriString = uri.toString()
                _uiState.update { it.copy(nationalIdUrls = it.nationalIdUrls + fileUriString) }
            } finally {
                _isUploading.value = false
            }
        }
    }

    fun removeNationalId(url: String) {
        _uiState.update { it.copy(nationalIdUrls = it.nationalIdUrls.filter { item -> item != url }) }
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

    fun updateSelectedType(type: String) {
        _uiState.update { it.copy(selectedType = type) }
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

    fun updateCopiesCount(count: Int) {
        _uiState.update {
            it.copy(
                copiesCount = count,
                totalFees = count * PRICE_PER_COPY
            )
        }
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