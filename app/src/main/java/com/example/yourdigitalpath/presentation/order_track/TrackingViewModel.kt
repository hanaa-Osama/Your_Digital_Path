package com.example.yourdigitalpath.presentation.order_track

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourdigitalpath.domain.model.OrderTrackingDetail
import com.example.yourdigitalpath.domain.usecase.AddOrderUseCase
import com.example.yourdigitalpath.domain.usecase.ObserveOrderTrackingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TrackingViewModel @Inject constructor(
    private val observeOrderTrackingUseCase: ObserveOrderTrackingUseCase,
    private val addOrderUseCase: AddOrderUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<OrderTrackingDetail?>(null)
    val state: StateFlow<OrderTrackingDetail?> = _state

    fun startTracking(orderId: String) {
        viewModelScope.launch {
            observeOrderTrackingUseCase(orderId).collect { detail ->
                if (detail != null) {
                    _state.value = detail
                } else {
                    println("DEBUG: لا توجد بيانات لهذا الـ ID: $orderId")
                }

            }
        }
    }

    fun createDigitalIdentityOrder() {
        viewModelScope.launch {
            try {
                addOrderUseCase("هوية رقمية")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun createOrderTest(
        serviceName: String
    ) {
        viewModelScope.launch {
            try {
                addOrderUseCase(serviceName)
                startTracking("REQ-2025-00842")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}