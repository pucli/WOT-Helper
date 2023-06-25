package com.wot.helper.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.wot.helper.common.Constants
import com.wot.helper.domain.models.use_case.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    @Named(Constants.IO_DISPATCHER)
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val isLoggedIn =
        authUseCases.isLoggedIn()

    val userProfile =
        authUseCases.getUserProfile().asLiveData(ioDispatcher + viewModelScope.coroutineContext)

    fun signOut() = liveData(ioDispatcher + viewModelScope.coroutineContext) {
        authUseCases.signOut().collect { response ->
            emit(response)
        }
    }
}