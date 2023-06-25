package com.wot.helper.domain.models.use_case.auth

import com.wot.helper.R
import com.wot.helper.common.Constants.MIN_PASSWORD_LENGTH
import com.wot.helper.common.UiText

class ValidatePassword {

    operator fun invoke(password: String): ValidationResponse {
        if (password.isBlank()) {
            return ValidationResponse(
                isValid = false,
                errorMessage = UiText.StringResource(R.string.required)
            )
        }
        if (password.length < MIN_PASSWORD_LENGTH) {
            return ValidationResponse(
                isValid = false,
                errorMessage = UiText.StringResource(
                    R.string.min_length_password_error,
                    MIN_PASSWORD_LENGTH
                )
            )
        }
        return ValidationResponse(
            isValid = true
        )
    }
}