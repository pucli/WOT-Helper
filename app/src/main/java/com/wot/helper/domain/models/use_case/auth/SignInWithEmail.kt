package com.wot.helper.domain.models.use_case.auth

import com.wot.helper.domain.models.repository.AuthRepository

class SignInWithEmail(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) = repository.firebaseSignInWithEmailAndPassword(email, password)
}