package com.wot.helper.ui.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.wot.helper.common.Constants
import com.wot.helper.domain.models.models.profileinfo.ProfileInfo
import com.wot.helper.domain.models.repository.AuthRepository
import com.wot.helper.domain.models.repository.RetrofitInstanceProfile
import com.wot.helper.domain.models.use_case.auth.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @Named(Constants.IO_DISPATCHER)
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val isLoggedIn = authRepository.isUserLoggedIn()

    val profileInfo = MutableLiveData<ProfileInfo?>()

    init {
        // Fetch profile data when ViewModel is initialized
        loadProfileData()
    }

    private var currentUsername: String? = null

    private fun loadProfileData() {
        if (!isLoggedIn) return
        viewModelScope.launch(ioDispatcher) {
            val userResponse = try {
                authRepository.getUserProfile().first()
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Failed to fetch user profile", e)
                return@launch
            }
            when (userResponse) {
                is Response.Success -> {
                    currentUsername = userResponse.data.username
                    Log.i("ProfileViewModel", "Current Username: $currentUsername")
                    val accountId = userResponse.data.account_id
                    if (accountId != null) {
                        fetchWargamingProfile(accountId)
                    } else {
                        Log.e("ProfileViewModel", "Account ID is null")
                        // Post profile with just the username if accountId is null
                        profileInfo.postValue(ProfileInfo(nickname = currentUsername))
                    }
                }
                is Response.Failure -> {
                    Log.e("ProfileViewModel", "Failed to get user: ${userResponse.errorMessage}")
                }
            }
        }
    }

    private suspend fun fetchWargamingProfile(accountId: String) {
        try {
            val response = RetrofitInstanceProfile.api.getInfoProfile(
                applicationId = "ace14516f4be72cde04425adca560339",
                accountId = accountId
            )
            if (response.isSuccessful) {
                val profileData = response.body()?.data?.get(accountId)
                if (profileData != null) {
                    profileInfo.postValue(profileData)
                } else {
                    // Create ProfileInfo with just the username if data is null
                    val defaultProfile = ProfileInfo(nickname = currentUsername)
                    profileInfo.postValue(defaultProfile)
                }
                Log.e("ProfileViewModel", "Fetched profile data: $profileData")
            } else {
                Log.e("ProfileViewModel", "API response unsuccessful: ${response.code()}")
                profileInfo.postValue(ProfileInfo(nickname = currentUsername))
            }
        } catch (e: Exception) {
            Log.e("ProfileViewModel", "Error: ${e.message}")
            profileInfo.postValue(ProfileInfo(nickname = currentUsername))
        }
    }

    fun signOut() = liveData<Response<Any>>(ioDispatcher + viewModelScope.coroutineContext) {
        authRepository.signOut().collect { response ->
            emit(response)
        }
    }
}