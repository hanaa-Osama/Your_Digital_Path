package com.example.yourdigitalpath.presentation.Register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.data.local.Dao.UserProfileDao
import com.example.yourdigitalpath.data.local.entity.UserProfileEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    object Success : RegisterState()
    data class Error(
        val messageRes: Int
    ) : RegisterState()
}

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userProfileDao: UserProfileDao
) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    var fullName = ""
    var nationalId = ""
    var birthDate = ""
    var phone = ""
    private val _state =
        MutableStateFlow<RegisterState>(
            RegisterState.Idle
        )
    val state = _state.asStateFlow()
    fun register(
        email: String,
        password: String
    ) {
        _state.value = RegisterState.Loading
        viewModelScope.launch {
            try {
                val result =
                    auth.createUserWithEmailAndPassword(
                        email.trim(),
                        password
                    ).await()
                val user =
                    result.user
                        ?: throw Exception()
                val profileUpdate =
                    UserProfileChangeRequest.Builder()
                        .setDisplayName(fullName)
                        .build()
                user.updateProfile(profileUpdate).await()
                val userData = hashMapOf(
                    "uid" to user.uid,
                    "fullName" to fullName,
                    "nationalId" to nationalId,
                    "email" to email.trim(),
                    "phone" to phone,
                    "birthDate" to birthDate,
                    "createdAt" to System.currentTimeMillis()
                )
                firestore.collection("users")
                    .document(user.uid)
                    .set(userData)
                    .await()
                user.reload().await()
                userProfileDao.insertUserProfile(
                    UserProfileEntity(
                        nationalId = nationalId,
                        name = fullName,
                        email = email.trim(),
                        phoneNumber = phone,
                        governorate = null
                    )
                )
                _state.value = RegisterState.Success
            } catch (e: FirebaseAuthWeakPasswordException) {
                _state.value =
                    RegisterState.Error(
                        R.string.weak_password
                    )
            } catch (e: FirebaseAuthUserCollisionException) {
                _state.value =
                    RegisterState.Error(
                        R.string.email_already_used
                    )
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                _state.value =
                    RegisterState.Error(
                        R.string.invalid_email
                    )
            } catch (e: Exception) {
                _state.value =
                    RegisterState.Error(
                        R.string.general_error
                    )
            }
        }
    }
    fun resetState() {
        _state.value = RegisterState.Idle
    }
}