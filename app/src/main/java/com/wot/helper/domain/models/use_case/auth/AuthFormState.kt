package com.wot.helper.domain.models.use_case.auth


import com.wot.helper.R
import com.wot.helper.common.UiText

data class AuthFormState(
    val hasNoError: Boolean,
    val usernameError: UiText.StringResource? = UiText.StringResource(R.string.null_string),
    val emailError: UiText.StringResource? = UiText.StringResource(R.string.null_string),
    val passwordError: UiText.StringResource? = UiText.StringResource(R.string.null_string),
    val confirmedPasswordError: UiText.StringResource? = UiText.StringResource(R.string.null_string)
)