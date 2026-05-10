package com.example.yourdigitalpath.presentation.Register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourdigitalpath.data.local.Dao.UserProfileDao
import com.example.yourdigitalpath.data.local.entity.UserProfileEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

sealed class RegisterState {
    object Idle    : RegisterState()
    object Loading : RegisterState()
    object Success : RegisterState()
    data class Error(val message: String) : RegisterState()
}

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userProfileDao: UserProfileDao
) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    var fullName   = ""
    var nationalId = ""
    var birthDate  = ""
    var phone      = ""

    private val _state = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val state = _state.asStateFlow()

    fun register(email: String, password: String) {
        _state.value = RegisterState.Loading

        val firebaseEmail = "$nationalId@digitalpath.app"

        viewModelScope.launch {
            try {
                val result = auth.createUserWithEmailAndPassword(firebaseEmail, password).await()
                val user = result.user ?: throw Exception("فشل إنشاء الحساب")

                val profileUpdate = UserProfileChangeRequest.Builder()
                    .setDisplayName(fullName)
                    .build()
                user.updateProfile(profileUpdate).await()

                userProfileDao.insertUserProfile(
                    UserProfileEntity(
                        nationalId  = nationalId,
                        name        = fullName,
                        email       = email,
                        phoneNumber = phone,
                        governorate = null
                    )
                )

                _state.value = RegisterState.Success

            } catch (e: Exception) {
                _state.value = RegisterState.Error(
                    when {
                        e.message?.contains("email address is already") == true ->
                            "هذا الرقم القومي مسجل بالفعل"
                        e.message?.contains("password") == true ->
                            "كلمة المرور ضعيفة جداً"
                        else -> "حدث خطأ، حاول مرة أخرى"
                    }
                )
            }
        }
    }
    fun resetState() {
        _state.value = RegisterState.Idle
    }
}