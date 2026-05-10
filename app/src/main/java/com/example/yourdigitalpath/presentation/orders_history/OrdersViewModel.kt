package com.example.yourdigitalpath.presentation.orders_history


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourdigitalpath.domain.model.OrderModel
import com.example.yourdigitalpath.domain.model.OrderStatus
import com.example.yourdigitalpath.domain.usecase.GetMyOrdersUseCase
import com.example.yourdigitalpath.domain.usecase.GetOrdersByStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val getMyOrdersUseCase: GetMyOrdersUseCase,
    private val getOrdersByStatusUseCase: GetOrdersByStatusUseCase
) : ViewModel() {

    private val _selectedStatus = MutableStateFlow<OrderStatus?>(null)
    val selectedStatus = _selectedStatus.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val orders: StateFlow<List<OrderModel>> = combine(
        getMyOrdersUseCase(),
        _selectedStatus
    ) { allOrders, status ->
        if (status == null) {
            allOrders
        } else {
            allOrders.filter { it.status == status }
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