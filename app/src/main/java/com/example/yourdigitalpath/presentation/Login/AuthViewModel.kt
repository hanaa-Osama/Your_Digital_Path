package com.example.yourdigitalpath.presentation.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourdigitalpath.data.local.Dao.UserProfileDao
import com.example.yourdigitalpath.presentation.Login.component.SavedAccount
import com.example.yourdigitalpath.presentation.Login.component.SavedAccountsManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import android.content.Context
import com.example.yourdigitalpath.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(
        val token: String,
        val userName: String
    ) : LoginState()
    data class Error(
        val message: String
    ) : LoginState()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val savedAccountsManager: SavedAccountsManager,
    private val userProfileDao: UserProfileDao
) : ViewModel() {
    private val _loginState =
        MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState = _loginState.asStateFlow()
    private val _userName =
        MutableStateFlow(context.getString(R.string.default_user))
    val userName: StateFlow<String> = _userName
    private val _savedAccounts =
        MutableStateFlow<List<SavedAccount>>(emptyList())
    val savedAccounts = _savedAccounts.asStateFlow()
    private var loginedFromInit = false

    init {
        _savedAccounts.value =
            savedAccountsManager.getAccounts()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            loginedFromInit = true
            viewModelScope.launch {
                val name =
                    fetchUserName(currentUser.uid)
                _userName.value = name
                _loginState.value =
                    LoginState.Success(
                        token = currentUser.uid,
                        userName = name
                    )
            }
        }
    }

    fun isUserAlreadyLoggedIn(): Boolean {
        return loginedFromInit
    }

    fun login(
        email: String,
        password: String
    ) {
        if (email.isBlank() || password.isBlank()) {
            _loginState.value =
                LoginState.Error(
                    context.getString(
                        R.string.enter_email_and_password
                    )
                )
            return
        }
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            try {
                val result =
                    auth.signInWithEmailAndPassword(
                        email.trim(),
                        password
                    ).await()
                val user =
                    result.user
                        ?: throw Exception(
                            context.getString(R.string.login_failed)
                        )
                user.reload().await()
                val name =
                    fetchUserName(user.uid)
                _userName.value = name
                savedAccountsManager.saveAccount(
                    email = email.trim(),
                    name = name
                )
                _savedAccounts.value =
                    savedAccountsManager.getAccounts()
                _loginState.value =
                    LoginState.Success(
                        token = user.uid,
                        userName = name
                    )
            } catch (e: Exception) {
                _loginState.value =
                    LoginState.Error(
                        context.getString(
                            R.string.invalid_email_or_password
                        )
                    )
            }
        }
    }

    fun removeAccount(email: String) {

        savedAccountsManager.removeAccount(email)

        _savedAccounts.value =
            savedAccountsManager.getAccounts()
    }

    private suspend fun fetchUserName(uid: String): String {
        return try {
            val doc =
                firestore.collection("users")
                    .document(uid)
                    .get()
                    .await()
            val firestoreName =
                doc.getString("fullName")
            when {
                !firestoreName.isNullOrBlank() -> {
                    firestoreName
                }
                !auth.currentUser?.displayName.isNullOrBlank() -> {
                    auth.currentUser?.displayName ?: context.getString(R.string.default_user)
                }
                else -> {
                    context.getString(R.string.default_user)
                }
            }
        } catch (e: Exception) {

            auth.currentUser?.displayName ?: context.getString(R.string.default_user)
        }
    }

    fun refreshAfterRegister() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            viewModelScope.launch {
                currentUser.reload().await()
                val name =
                    fetchUserName(currentUser.uid)
                _userName.value = name
                savedAccountsManager.saveAccount(
                    email = currentUser.email ?: "",
                    name = name
                )
                _savedAccounts.value =
                    savedAccountsManager.getAccounts()
                _loginState.value =
                    LoginState.Success(
                        token = currentUser.uid,
                        userName = name
                    )
            }
        }
    }

    fun logout() {
        auth.signOut()
        loginedFromInit = false
        _userName.value = context.getString(R.string.default_user)
        _loginState.value = LoginState.Idle
    }

    fun resetState() {

        _loginState.value = LoginState.Idle
    }

    suspend fun getSavedUserName(): String {
        return userProfileDao.getUserName()
            ?: context.getString(R.string.default_user)
    }
}