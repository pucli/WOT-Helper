package com.wot.helper.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.wot.helper.common.Constants
import com.wot.helper.domain.models.repository.AuthRepository
import com.wot.helper.domain.models.use_case.auth.Response

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @Named(Constants.IO_DISPATCHER)
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val isLoggedIn =
        authRepository.isUserLoggedIn()

    val userProfile =
        authRepository.getUserProfile().asLiveData(ioDispatcher + viewModelScope.coroutineContext)

    fun signOut() = liveData<Response<Any>>(ioDispatcher + viewModelScope.coroutineContext) {
        authRepository.signOut().collect { response ->
            emit(response)
        }
    }
}