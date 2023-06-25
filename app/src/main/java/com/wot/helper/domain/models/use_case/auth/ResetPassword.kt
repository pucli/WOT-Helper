package com.wot.helper.domain.models.use_case.auth

import com.wot.helper.domain.models.repository.AuthRepository

class ResetPassword(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String) = repository.sendPasswordResetEmail(email)
}