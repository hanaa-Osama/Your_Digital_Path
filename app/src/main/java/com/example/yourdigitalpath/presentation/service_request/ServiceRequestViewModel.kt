package com.example.yourdigitalpath.presentation.service_request

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourdigitalpath.domain.model.ServiceRequestModel
import com.example.yourdigitalpath.domain.usecase.GetLastServiceRequestUseCase
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
    private val saveServiceRequestUseCase: SaveServiceRequestUseCase,
    private val getLastServiceRequestUseCase: GetLastServiceRequestUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ServiceRequestModel())
    val uiState: StateFlow<ServiceRequestModel> = _uiState.asStateFlow()

    init {
        loadLastServiceRequest()
    }

    private fun loadLastServiceRequest() {
        viewModelScope.launch {
            getLastServiceRequestUseCase()?.let { lastRequest ->
                _uiState.update { lastRequest }
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
                // Handle error
            }
        }
    }
}