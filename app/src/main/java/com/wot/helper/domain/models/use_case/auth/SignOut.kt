package com.wot.helper.domain.models.use_case.auth

import com.wot.helper.domain.models.repository.AuthRepository

class SignOut(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.signOut()
}