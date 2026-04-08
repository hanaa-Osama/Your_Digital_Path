package com.example.yourdigitalpath.presentation.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourdigitalpath.domain.model.UserProfile
import com.example.yourdigitalpath.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val toggleNotificationsUseCase: ToggleNotificationsUseCase,
    private val getNotificationSettingsUseCase: GetNotificationSettingsUseCase
) : ViewModel() {

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile = _userProfile.asStateFlow()

    private val _notificationsEnabled = MutableStateFlow(false)
    val notificationsEnabled = _notificationsEnabled.asStateFlow()

    private val _updateResult = MutableStateFlow<Result<Unit>?>(null)
    val updateResult = _updateResult.asStateFlow()

    init {
        loadProfileData()
    }

    private fun loadProfileData() {
        viewModelScope.launch {
            _userProfile.value = getUserProfileUseCase()
            _notificationsEnabled.value = getNotificationSettingsUseCase()
        }
    }

    fun updateProfile(updatedProfile: UserProfile) {
        viewModelScope.launch {
            val result = updateUserProfileUseCase(updatedProfile)
            _updateResult.value = result

            if (result.isSuccess) {
                _userProfile.value = updatedProfile
            }
        }
    }

    fun toggleNotifications(isEnabled: Boolean) {
        viewModelScope.launch {
            toggleNotificationsUseCase(isEnabled)
            _notificationsEnabled.value = isEnabled
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }

    fun resetUpdateResult() {
        _updateResult.value = null
    }
}