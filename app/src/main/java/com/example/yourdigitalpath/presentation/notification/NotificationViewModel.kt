package com.example.yourdigitalpath.presentation.notification

import android.app.NotificationManager
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourdigitalpath.domain.model.NotificationItem
import com.example.yourdigitalpath.domain.usecase.ClearNotificationsUseCase
import com.example.yourdigitalpath.domain.usecase.DeleteNotificationUseCase
import com.example.yourdigitalpath.domain.usecase.GetNotificationsUseCase
import com.example.yourdigitalpath.domain.usecase.MarkNotificationAsReadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val markNotificationAsReadUseCase: MarkNotificationAsReadUseCase,
    private val clearNotificationsUseCase: ClearNotificationsUseCase,
    private val deleteNotificationUseCase: DeleteNotificationUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val notifications: StateFlow<List<NotificationItem>> = getNotificationsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.Lazily,
            initialValue = emptyList()
        )

    fun onNotificationClicked(id: String) {
        viewModelScope.launch {
            markNotificationAsReadUseCase(id)
        }
    }

    fun deleteNotification(id: String) {
        viewModelScope.launch {
            deleteNotificationUseCase(id)
            // حذف الإشعار من شريط النظام إذا كان موجوداً
            try {
                // نستخدم hashCode للمعرف للتأكد من مطابقة الـ ID الذي استُخدم عند الإرسال
                notificationManager.cancel(id.hashCode())
            } catch (e: Exception) {
            }
        }
    }

    fun clearAllNotifications() {
        viewModelScope.launch {
            clearNotificationsUseCase()
            notificationManager.cancelAll()
        }
    }
}