package com.example.yourdigitalpath.data.mapper
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.Color
import com.example.yourdigitalpath.data.dataSource.local.Entity.NotificationEntity
import com.example.yourdigitalpath.data.model.TrackingFirebaseDto
import com.example.yourdigitalpath.domain.model.NotificationItem
import com.example.yourdigitalpath.domain.model.TrackingStep
import com.example.yourdigitalpath.presentation.notification.screen.NotificationItemData

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

fun TrackingFirebaseDto.toDomain(stepId: Long = 0L): TrackingStep {
    return TrackingStep(
        id = stepId,
        title = this.description,
        timestamp = this.update_time,
        status = this.status_code
    )
}

fun NotificationItem.toUiData(): NotificationItemData {
    val icon = when (type) {
        "info" -> Icons.Default.Notifications
        "success" -> Icons.Default.Check
        "warning" -> Icons.Default.Person
        "payment" -> Icons.Default.Person
        else -> Icons.Default.Notifications
    }
    val color = when (type) {
        "success" -> Color(0xFF4CAF50)
        "warning" -> Color(0xFFFFA000)
        "payment" -> Color(0xFF3F51B5)
        else -> Color.Gray
    }
    return NotificationItemData(
        title = title,
        desc = message,
        time = timeAgo,
        icon = icon,
        color = color
    )
}