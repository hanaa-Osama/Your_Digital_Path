package com.example.yourdigitalpath.data.mapper

import com.example.yourdigitalpath.data.dataSource.local.NotificationEntity
import com.example.yourdigitalpath.data.model.TrackingFirebaseDto
import com.example.yourdigitalpath.domain.model.NotificationItem
import com.example.yourdigitalpath.domain.model.TrackingStep

fun NotificationEntity.toDomain(): NotificationItem {
    return NotificationItem(
        id = this.id,
        title = this.title,
        message = this.message,
        timeAgo = formatTimestamp(this.createdAt),
        type = this.type,
        isRead = this.isRead
    )
}

fun formatTimestamp(timestamp: Long): String {
    val diff = System.currentTimeMillis() - timestamp
    return when {
        diff < 60_000 -> "الآن"
        diff < 3600_000 -> "${diff / 60_000} دقيقة"
        diff < 86400_000 -> "منذ ساعات"
        else -> "منذ أيام"
    }
}

fun TrackingFirebaseDto.toDomain(stepId: Int = 0): TrackingStep {
    return TrackingStep(
        id = stepId,
        title = this.description,
        timestamp = this.update_time,
        status = this.status_code
    )
}