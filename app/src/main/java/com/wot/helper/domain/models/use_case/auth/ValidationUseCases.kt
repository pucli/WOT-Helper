package com.wot.helper.domain.models.use_case.auth

data class ValidationUseCases(
    val validateEmail: ValidateEmail,
    val validateConfirmedPassword: ValidateConfirmedPassword,
    val validatePassword: ValidatePassword,
    val validateUsername: ValidateUsername
)