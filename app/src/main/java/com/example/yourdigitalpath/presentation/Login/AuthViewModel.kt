package com.blqes.digi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourdigitalpath.domain.NationalIdValidator
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

sealed class LoginState {
    object Idle    : LoginState()
    object Loading : LoginState()
    data class Success(val token: String, val userName: String) : LoginState()
    data class Error(val message: String) : LoginState()
}

class AuthViewModel : ViewModel() {

    var loginState by mutableStateOf<LoginState>(LoginState.Idle)
        private set

    private val auth = FirebaseAuth.getInstance()

    fun login(nationalId: String, password: String) {
        if (!NationalIdValidator.isValid(nationalId)) {
            loginState = LoginState.Error("الرقم القومي غير صالح — يجب أن يكون 14 رقم")
            return
        }

        loginState = LoginState.Loading

        val email = "$nationalId@digitalpath.app"

        viewModelScope.launch {
            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                val user   = result.user
                loginState = LoginState.Success(
                    token    = user?.uid ?: "",
                    userName = user?.displayName ?: nationalId
                )
            } catch (e: Exception) {
                loginState = LoginState.Error("الرقم القومي أو كلمة المرور غير صحيحة")
            }
        }
    }

    fun logout() {
        auth.signOut()
        loginState = LoginState.Idle
    }
}