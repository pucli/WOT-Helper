package com.wot.helper.domain.models.use_case.auth

import com.wot.helper.R
import com.wot.helper.common.UiText

class ValidateConfirmedPassword {

    operator fun invoke(password: String, confirmedPassword: String): ValidationResponse {
        if (password != confirmedPassword) {
            return ValidationResponse(
                isValid = false,
                errorMessage = UiText.StringResource(R.string.invalid_confirm_password_error)
            )
        }
        return ValidationResponse(
            isValid = true
        )
    }
}