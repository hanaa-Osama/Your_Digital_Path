package com.example.yourdigitalpath.presentation.order_track

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourdigitalpath.domain.model.OrderTrackingDetail
import com.example.yourdigitalpath.domain.usecase.ObserveOrderTrackingUseCase
import com.example.yourdigitalpath.domain.usecase.SubmitFinalOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TrackingViewModel @Inject constructor(
    private val observeOrderTrackingUseCase: ObserveOrderTrackingUseCase,
    private val submitFinalOrderUseCase: SubmitFinalOrderUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<OrderTrackingDetail?>(null)
    val state: StateFlow<OrderTrackingDetail?> = _state

    private val _orderId = MutableStateFlow<String?>(null)
    val orderId: StateFlow<String?> = _orderId

    fun confirmAndSubmitOrder(onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val newId = submitFinalOrderUseCase()
                _orderId.value = newId
                startTracking(newId)
                onSuccess(newId)
            } catch (e: Exception) {
            }
        }
    }

    fun startTracking(orderId: String) {
        viewModelScope.launch {
            observeOrderTrackingUseCase(orderId).collect { detail ->
                _state.value = detail
            }
        }
    }
    fun testMyFirebaseUpload() {
        viewModelScope.launch {
            try {
                val testId = submitFinalOrderUseCase()
                println("DEBUG: تم الرفع بنجاح بـ ID: $testId")
                startTracking(testId) // جرب تعرضه كمان
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}