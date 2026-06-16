package com.example.yourdigitalpath.data.mapper

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.Color
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.data.dataSource.local.Entity.NotificationEntity
import com.example.yourdigitalpath.data.model.TrackingFirebaseDto
import com.example.yourdigitalpath.domain.model.NotificationItem
import com.example.yourdigitalpath.domain.model.TrackingStep
import com.example.yourdigitalpath.presentation.notification.screen.NotificationItemData

fun NotificationEntity.toDomain(context: Context): NotificationItem {
    return NotificationItem(
        id = this.id,
        title = this.title,
        message = this.message,
        timeAgo = formatTimestamp(context, this.createdAt),
        type = this.type,
        isRead = this.isRead
    )
}

fun formatTimestamp(context: Context, timestamp: Long): String {
    val diff = System.currentTimeMillis() - timestamp
    return when {
        diff < 60_000 ->
            context.getString(R.string.now)
        diff < 3600_000 ->
            context.getString(
                R.string.minutes_ago,
                (diff / 60_000).toInt()
            )
        diff < 86400_000 ->
            context.getString(R.string.hours_ago)
        else ->
            context.getString(R.string.days_ago)
    }
}

fun TrackingFirebaseDto.toDomain(stepId: Long = 0L): TrackingStep {
    return TrackingStep(
        id = stepId,
        title = this.title,
        timestamp = this.timestamp,
        status = this.status,
        description = this.description
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