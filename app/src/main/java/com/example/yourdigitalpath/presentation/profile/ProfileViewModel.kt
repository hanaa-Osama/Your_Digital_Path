package com.example.yourdigitalpath.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourdigitalpath.domain.model.AppSettingsModel
import com.example.yourdigitalpath.domain.model.NotificationSettingsModel
import com.example.yourdigitalpath.domain.model.UserProfileModel
import com.example.yourdigitalpath.domain.usecase.GetAppSettingsUseCase
import com.example.yourdigitalpath.domain.usecase.GetNotificationSettingsUseCase
import com.example.yourdigitalpath.domain.usecase.GetUserProfileUseCase
import com.example.yourdigitalpath.domain.usecase.LogoutUseCase
import com.example.yourdigitalpath.domain.usecase.ToggleOffersNotificationsUseCase
import com.example.yourdigitalpath.domain.usecase.ToggleOrderNotificationsUseCase
import com.example.yourdigitalpath.domain.usecase.ToggleSystemNotificationsUseCase
import com.example.yourdigitalpath.domain.usecase.UpdateDisplayModeUseCase
import com.example.yourdigitalpath.domain.usecase.UpdateLanguageUseCase
import com.example.yourdigitalpath.domain.usecase.UpdateUserProfileUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
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

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _userProfileModel = MutableStateFlow<UserProfileModel?>(null)
    val userProfile = _userProfileModel.asStateFlow()

    private val _notificationSettingsModel =
        MutableStateFlow<NotificationSettingsModel?>(null)
    val notificationSettings = _notificationSettingsModel.asStateFlow()
    private val _appSettingsModel =
        MutableStateFlow<AppSettingsModel?>(null)
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
            try {
                val currentUser = auth.currentUser
                println("CURRENT USER = $currentUser")
                val uid = currentUser?.uid ?: run {
                    println("USER IS NULL")
                    return@launch
                }
                val document = firestore
                    .collection("users")
                    .document(uid)
                    .get()
                    .await()
                println("DOCUMENT EXISTS = ${document.exists()}")
                if (document.exists()) {
                    _userProfileModel.value = UserProfileModel(
                        name = document.getString("fullName") ?: "مستخدم",
                        nationalId = document.getString("nationalId") ?: "",
                        email = document.getString("email") ?: "",
                        phoneNumber = document.getString("phone") ?: "",
                        governorate = document.getString("governorate")
                    )
                } else {
                    println("USER DOCUMENT NOT FOUND")
                }
            } catch (e: Exception) {
                println("FIRESTORE ERROR = ${e.message}")
            }
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
            _notificationSettingsModel.value =
                _notificationSettingsModel.value?.copy(
                    orderNotifications = isEnabled
                )
        }
    }

    fun toggleOffersNotifications(isEnabled: Boolean) {
        viewModelScope.launch {
            toggleOffersNotificationsUseCase(isEnabled)
            _notificationSettingsModel.value =
                _notificationSettingsModel.value?.copy(
                    offersNotifications = isEnabled
                )
        }
    }

    fun toggleSystemNotifications(isEnabled: Boolean) {
        viewModelScope.launch {
            toggleSystemNotificationsUseCase(isEnabled)
            _notificationSettingsModel.value =
                _notificationSettingsModel.value?.copy(
                    systemNotifications = isEnabled
                )
        }
    }

    fun updateLanguage(language: String) {
        viewModelScope.launch {
            updateLanguageUseCase(language)
            _appSettingsModel.value =
                _appSettingsModel.value?.copy(
                    language = language
                )
        }
    }

    fun updateDisplayMode(mode: String) {
        viewModelScope.launch {
            updateDisplayModeUseCase(mode)
            _appSettingsModel.value =
                _appSettingsModel.value?.copy(
                    displayMode = mode
                )
        }
    }

    fun logout() {
        viewModelScope.launch {
            auth.signOut()
            logoutUseCase()
        }
    }

    fun resetUpdateResult() {
        _updateResult.value = null
    }
}