package com.example.yourdigitalpath.presentation.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourdigitalpath.domain.model.AppSettingsModel
import com.example.yourdigitalpath.domain.model.NotificationSettingsModel
import com.example.yourdigitalpath.domain.model.UserProfileModel
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
    private val getNotificationSettingsUseCase: GetNotificationSettingsUseCase,
    private val toggleOrderNotificationsUseCase: ToggleOrderNotificationsUseCase,
    private val toggleOffersNotificationsUseCase: ToggleOffersNotificationsUseCase,
    private val toggleSystemNotificationsUseCase: ToggleSystemNotificationsUseCase,
    private val getAppSettingsUseCase: GetAppSettingsUseCase,
    private val updateLanguageUseCase: UpdateLanguageUseCase,
    private val updateDisplayModeUseCase: UpdateDisplayModeUseCase
) : ViewModel() {

    private val _userProfileModel = MutableStateFlow<UserProfileModel?>(null)
    val userProfile = _userProfileModel.asStateFlow()

    private val _notificationSettingsModel = MutableStateFlow<NotificationSettingsModel?>(null)
    val notificationSettings = _notificationSettingsModel.asStateFlow()

    private val _appSettingsModel = MutableStateFlow<AppSettingsModel?>(null)
    val appSettings = _appSettingsModel.asStateFlow()

    private val _notificationsEnabled = MutableStateFlow(false)
    val notificationsEnabled = _notificationsEnabled.asStateFlow()

    private val _updateResult = MutableStateFlow<Result<Unit>?>(null)
    val updateResult = _updateResult.asStateFlow()

    init {
        loadProfileData()
    }

    private fun loadProfileData() {
        viewModelScope.launch {
            _userProfileModel.value = getUserProfileUseCase()
            _notificationSettingsModel.value = getNotificationSettingsUseCase()
            _appSettingsModel.value = getAppSettingsUseCase()
        }
    }

    fun updateProfile(updatedProfile: UserProfileModel) {
        viewModelScope.launch {
            val result = updateUserProfileUseCase(updatedProfile)
            _updateResult.value = result

            if (result.isSuccess) {
                _userProfileModel.value = updatedProfile
            }
        }
    }


    fun toggleOrderNotifications(isEnabled: Boolean) {
        viewModelScope.launch {
            toggleOrderNotificationsUseCase(isEnabled)
            _notificationSettingsModel.value = _notificationSettingsModel.value?.copy(orderNotifications = isEnabled)
        }
    }

    fun toggleOffersNotifications(isEnabled: Boolean) {
        viewModelScope.launch {
            toggleOffersNotificationsUseCase(isEnabled)
            _notificationSettingsModel.value = _notificationSettingsModel.value?.copy(offersNotifications = isEnabled)
        }
    }

    fun toggleSystemNotifications(isEnabled: Boolean) {
        viewModelScope.launch {
            toggleSystemNotificationsUseCase(isEnabled)
            _notificationSettingsModel.value = _notificationSettingsModel.value?.copy(systemNotifications = isEnabled)
        }
    }

    fun updateLanguage(language: String) {
        viewModelScope.launch {
            updateLanguageUseCase(language)
            _appSettingsModel.value = _appSettingsModel.value?.copy(language = language)
        }
    }

    fun updateDisplayMode(mode: String) {
        viewModelScope.launch {
            updateDisplayModeUseCase(mode)
            _appSettingsModel.value = _appSettingsModel.value?.copy(displayMode = mode)
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