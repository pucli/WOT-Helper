package com.wot.helper.domain.models.repository

import com.wot.helper.domain.models.use_case.auth.Response
import com.wot.helper.domain.models.models.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun firebaseSignInWithGoogle(idToken: String): Flow<Response<Boolean>>

    suspend fun firebaseSignInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Response<Boolean>>


    suspend fun signOut(): Flow<Response<Boolean>>

    suspend fun sendPasswordResetEmail(email: String): Flow<Response<Boolean>>

    suspend fun register(email: String, password: String, username: String): Flow<Response<Boolean>>

    fun getUserAuthState(): Flow<Boolean>

    fun getUserProfile(): Flow<Response<User>>

    fun isUserLoggedIn(): Boolean
}