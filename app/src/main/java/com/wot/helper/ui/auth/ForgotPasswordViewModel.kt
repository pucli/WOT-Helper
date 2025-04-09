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
class ForgotPasswordViewModel @Inject constructor(
    private val useCases: AuthUseCases,
    private val validationUseCases: ValidationUseCases,
    @Named(Constants.IO_DISPATCHER)
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun resetPassword(email: String) = liveData<Response<Boolean>>(ioDispatcher + viewModelScope.coroutineContext) {
        useCases.resetPassword(email).collect { response ->
            emit(response)
        }
    }

    fun validateEmail(email: String): AuthFormState {
        val emailResponse = validationUseCases.validateEmail(email = email)

        val hasError = !emailResponse.isValid

        return AuthFormState(
            hasNoError = !hasError,
            emailError = emailResponse.errorMessage
        )
    }

}