package com.wot.helper.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.wot.helper.common.Constants
import com.wot.helper.domain.models.use_case.auth.AuthFormState
import com.wot.helper.domain.models.use_case.auth.AuthUseCases
import com.wot.helper.domain.models.use_case.auth.ValidationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val validationUseCases: ValidationUseCases,
    @Named(Constants.IO_DISPATCHER)
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val authState =
        authUseCases.getAuthState().asLiveData(ioDispatcher + viewModelScope.coroutineContext)

    fun firebaseSignInWithEmail(email: String, password: String) =
        liveData(ioDispatcher + viewModelScope.coroutineContext) {
            authUseCases.signInWithEmail(email, password).collect { response ->
                emit(response)
            }
        }

    fun firebaseSignInWithGoogle(idToken: String) =
        liveData(ioDispatcher + viewModelScope.coroutineContext) {
            authUseCases.signInWithGoogle(idToken).collect { response ->
                emit(response)
            }
        }


    fun validateLoginForm(email: String, password: String): AuthFormState {
        val emailResponse = validationUseCases.validateEmail(email = email)
        val passwordResponse = validationUseCases.validatePassword(password = password)

        val hasError = listOf(
            emailResponse,
            passwordResponse
        ).any { !it.isValid }

        return AuthFormState(
            hasNoError = !hasError,
            emailError = emailResponse.errorMessage,
            passwordError = passwordResponse.errorMessage
        )
    }

}