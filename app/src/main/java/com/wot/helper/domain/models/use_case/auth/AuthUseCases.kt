package com.wot.helper.domain.models.use_case.auth

data class AuthUseCases(
    val resetPassword: ResetPassword,
    val getUserProfile: GetUserProfile,
    val getAuthState: GetAuthState,
    val isLoggedIn: IsLoggedIn,
    val signInWithGoogle: SignInWithGoogle,
    val signOut: SignOut,
    val signInWithEmail: SignInWithEmail,
    val register: Register
)