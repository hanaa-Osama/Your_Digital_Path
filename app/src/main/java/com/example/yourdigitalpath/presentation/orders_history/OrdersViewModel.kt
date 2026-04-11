package com.example.yourdigitalpath.presentation.orders_history


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourdigitalpath.domain.model.OrderModel
import com.example.yourdigitalpath.domain.model.OrderStatus
import com.example.yourdigitalpath.domain.usecase.GetMyOrdersUseCase
import com.example.yourdigitalpath.domain.usecase.GetOrdersByStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val getMyOrdersUseCase: GetMyOrdersUseCase,
    private val getOrdersByStatusUseCase: GetOrdersByStatusUseCase
) : ViewModel() {

    private val _selectedStatus = MutableStateFlow<OrderStatus?>(null)
    val selectedStatus = _selectedStatus.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val orders: StateFlow<List<OrderModel>> = _selectedStatus
        .flatMapLatest { status ->
            if (status == null) {
                getMyOrdersUseCase()
            } else {
                getOrdersByStatusUseCase(status)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onStatusFilterChanged(status: OrderStatus?) {
        _selectedStatus.value = status
    }
}