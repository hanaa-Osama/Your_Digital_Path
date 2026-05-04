package com.example.yourdigitalpath.domain.usecase.order

import com.example.yourdigitalpath.domain.model.TrackingStep

class CalculateOrderPercentageUseCase {
    operator fun invoke(steps: List<TrackingStep>): Int {
        if (steps.isEmpty()) return 0
        val completedSteps = steps.count { it.status.lowercase() == "completed" }
        return (completedSteps * 100) / steps.size
    }
}