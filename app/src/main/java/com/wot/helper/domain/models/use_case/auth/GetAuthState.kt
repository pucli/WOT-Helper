package com.wot.helper.domain.models.use_case.auth

import com.wot.helper.domain.models.repository.AuthRepository

class GetAuthState(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.getUserAuthState()
}