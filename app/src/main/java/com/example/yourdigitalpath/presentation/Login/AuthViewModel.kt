package com.example.yourdigitalpath.presentation.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val token: String, val userName: String) : LoginState()
    data class Error(val message: String) : LoginState()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState = _loginState.asStateFlow()

    init {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            viewModelScope.launch {
                val name = fetchUserName(currentUser.uid)
                _loginState.value = LoginState.Success(
                    token = currentUser.uid,
                    userName = name
                )
            }
        }
    }

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _loginState.value = LoginState.Error("من فضلك أدخل البريد الإلكتروني وكلمة المرور")
            return
        }
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            try {
                val result = auth.signInWithEmailAndPassword(email.trim(), password).await()
                val user = result.user ?: throw Exception("فشل تسجيل الدخول")
                val name = fetchUserName(user.uid)
                _loginState.value = LoginState.Success(token = user.uid, userName = name)
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("البريد الإلكتروني أو كلمة المرور غير صحيحة")
            }
        }
    }

    private suspend fun fetchUserName(uid: String): String {
        return try {
            val doc = firestore.collection("users").document(uid).get().await()
            doc.getString("fullName") ?: auth.currentUser?.displayName ?: "مستخدم"
        } catch (e: Exception) {
            auth.currentUser?.displayName ?: "مستخدم"
        }
    }

    fun logout() {
        auth.signOut()
        _loginState.value = LoginState.Idle
    }

    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}