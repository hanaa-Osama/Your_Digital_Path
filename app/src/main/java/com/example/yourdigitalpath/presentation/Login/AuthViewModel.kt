package com.blqes.digi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourdigitalpath.domain.NationalIdValidator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val token: String, val userName: String) : LoginState()
    data class Error(val message: String) : LoginState()
}

class AuthViewModel : ViewModel() {

    var loginState by mutableStateOf<LoginState>(LoginState.Idle)
        private set

    fun login(nationalId: String, password: String) {
        if (!NationalIdValidator.isValid(nationalId)) {
            loginState = LoginState.Error("الرقم القومي غير صالح")
            return
        }

        loginState = LoginState.Loading

        viewModelScope.launch {
            delay(2000)
            if (nationalId == "12345678901234" && password == "1234") {
                loginState = LoginState.Success(token = "FakeToken123", userName = "هناء اسامة")
            } else {
                loginState = LoginState.Error("بيانات الدخول غير صحيحة")
            }
        }
    }
}