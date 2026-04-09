package com.example.yourdigitalpath.domain.model

sealed interface OrderStatus {
    data object Pending : OrderStatus
    data object InProgress : OrderStatus
    data object Issued : OrderStatus
    data object Completed : OrderStatus

    data class Rejected(val reason: String) : OrderStatus
}