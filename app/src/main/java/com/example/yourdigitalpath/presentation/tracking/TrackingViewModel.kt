package com.example.yourdigitalpath.presentation.tracking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourdigitalpath.domain.model.OrderTrackingDetail
import com.example.yourdigitalpath.domain.usecase.ObserveOrderTrackingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackingViewModel @Inject constructor(
    private val observeOrderTrackingUseCase: ObserveOrderTrackingUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<OrderTrackingDetail?>(null)
    val state: StateFlow<OrderTrackingDetail?> = _state

    fun startTracking(orderId: String) {
        viewModelScope.launch {

            observeOrderTrackingUseCase(orderId).collect { detail ->
                _state.value = detail
            }
        }
    }
}