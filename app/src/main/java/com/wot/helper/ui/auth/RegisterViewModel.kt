package com.wot.helper.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.wot.helper.common.Constants
import com.wot.helper.domain.models.use_case.auth.AuthFormState
import com.wot.helper.domain.models.use_case.auth.AuthUseCases
import com.wot.helper.domain.models.use_case.auth.Response
import com.wot.helper.domain.models.use_case.auth.ValidationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val validationUseCases: ValidationUseCases,
    @Named(Constants.IO_DISPATCHER)
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun createAccount(email: String, password: String, username: String) =
        liveData<Response<Boolean>>(ioDispatcher + viewModelScope.coroutineContext) {
            authUseCases.register(email, password, username).collect { response ->
                emit(response)
            }
        }

    fun validateRegistrationForm(
        username: String,
        email: String,
        password: String,
        confirmedPassword: String
    ): AuthFormState {
        val usernameResponse = validationUseCases.validateUsername(username = username)
        val emailResponse = validationUseCases.validateEmail(email = email)
        val passwordResponse = validationUseCases.validatePassword(password = password)
        val confirmedPasswordResponse = validationUseCases.validateConfirmedPassword(
            password = password,
            confirmedPassword = confirmedPassword
        )
        val hasError = listOf(
            usernameResponse,
            emailResponse,
            passwordResponse,
            confirmedPasswordResponse
        ).any { !it.isValid }

        return AuthFormState(
            hasNoError = !hasError,
            usernameError = usernameResponse.errorMessage,
            emailError = emailResponse.errorMessage,
            passwordError = passwordResponse.errorMessage,
            confirmedPasswordError = confirmedPasswordResponse.errorMessage
        )
    }
}