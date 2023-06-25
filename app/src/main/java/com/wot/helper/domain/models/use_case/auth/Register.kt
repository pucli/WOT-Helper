package com.wot.helper.domain.models.use_case.auth

import com.wot.helper.domain.models.repository.AuthRepository

class Register(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String, username: String) = repository.register(email, password, username)
}