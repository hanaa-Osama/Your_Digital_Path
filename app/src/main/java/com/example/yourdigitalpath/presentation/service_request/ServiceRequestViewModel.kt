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

    private val _uiState = MutableStateFlow(
        ServiceRequestModel(
            selectedType = "",
            requestReason = "",
            otherReason = "",
            deliveryMethod = "",
            copiesCount = 0
        )
    )

    val uiState: StateFlow<ServiceRequestModel> = _uiState.asStateFlow()

    private val _isUploading = MutableStateFlow(false)
    val isUploading: StateFlow<Boolean> = _isUploading.asStateFlow()

    fun uploadNationalId(uri: Uri) {
        viewModelScope.launch {
            _isUploading.value = true
            try {
                val fakeUrl = "https://firebasestorage.com/national_id_${uri.lastPathSegment}"
                _uiState.update { it.copy(nationalIdUrl = fakeUrl) }
            } finally {
                _isUploading.value = false
            }
        }
    }

    fun uploadServiceDocument(uri: Uri) {
        viewModelScope.launch {
            _isUploading.value = true
            try {
                val fakeUrl = "https://firebasestorage.com/service_doc_${uri.lastPathSegment}"
                _uiState.update { it.copy(serviceDocumentUrl = fakeUrl) }
            } finally {
                _isUploading.value = false
            }
        }
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
        _uiState.update { it.copy(copiesCount = count) }
    }

    fun saveServiceRequest(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                saveServiceRequestUseCase(_uiState.value)
                onSuccess()
            } catch (e: Exception) {
            }
        }
    }
}
