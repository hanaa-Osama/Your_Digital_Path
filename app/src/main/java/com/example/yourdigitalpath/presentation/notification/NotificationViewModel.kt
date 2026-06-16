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
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
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
    private val auth: FirebaseAuth,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val currentUserId = auth.currentUser?.uid

    val notifications: StateFlow<List<NotificationItem>> = if (currentUserId != null) {
        getNotificationsUseCase(currentUserId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = emptyList()
            )
    } else {
        MutableStateFlow(emptyList())
    }

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
            currentUserId?.let {
                clearNotificationsUseCase(it)
                notificationManager.cancelAll()
            }
        }
    }
}