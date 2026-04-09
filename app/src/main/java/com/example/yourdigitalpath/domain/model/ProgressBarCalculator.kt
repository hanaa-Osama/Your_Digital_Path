package com.example.yourdigitalpath.domain.model

object ProgressBarCalculator {
    fun calculate(status: OrderStatus): Int {
        return when (status) {
            is OrderStatus.Pending -> 25
            is OrderStatus.InProgress -> 50
            is OrderStatus.Issued -> 75
            is OrderStatus.Completed -> 100
            is OrderStatus.Rejected -> 0
        }
    }
}