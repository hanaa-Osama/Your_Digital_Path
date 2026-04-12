package com.example.yourdigitalpath.presentation.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourdigitalpath.domain.model.NotificationItem
import com.example.yourdigitalpath.domain.usecase.ClearNotificationsUseCase
import com.example.yourdigitalpath.domain.usecase.GetNotificationsUseCase
import com.example.yourdigitalpath.domain.usecase.MarkNotificationAsReadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val markNotificationAsReadUseCase: MarkNotificationAsReadUseCase,
    private val clearNotificationsUseCase: ClearNotificationsUseCase
) : ViewModel() {

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
    fun clearAllNotifications() {
        viewModelScope.launch {
            clearNotificationsUseCase()
        }
    }
}