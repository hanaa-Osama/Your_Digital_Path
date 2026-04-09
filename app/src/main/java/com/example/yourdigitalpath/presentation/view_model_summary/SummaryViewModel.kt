package com.example.yourdigitalpath.presentation.view_model_summary

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourdigitalpath.domain.model.ServiceRequest
import com.example.yourdigitalpath.domain.usecase.SubmitRequestUseCase
import com.example.yourdigitalpath.domain.usecase.UploadFileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val submitRequestUseCase: SubmitRequestUseCase,
    private val uploadFileUseCase: UploadFileUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SummaryUiState>(SummaryUiState.Idle)
    val uiState: StateFlow<SummaryUiState> = _uiState.asStateFlow()

    fun uploadAndSubmit(uris: List<Uri>, request: ServiceRequest) {
        viewModelScope.launch {
            _uiState.value = SummaryUiState.Loading

            val result = uploadFileUseCase(uris)

            result.fold(
                onSuccess = { downloadUrls ->
                    val finalRequest = request.copy(documentUrls = downloadUrls)
                    submitRequestUseCase(finalRequest)
                    _uiState.value = SummaryUiState.Success
                },
                onFailure = { error ->
                    _uiState.value = SummaryUiState.Error(error.message ?: "حدث خطأ ما")
                }
            )
        }
    }
}