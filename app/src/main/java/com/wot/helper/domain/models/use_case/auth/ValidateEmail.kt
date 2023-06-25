package com.wot.helper.domain.models.use_case.auth

import android.util.Patterns
import com.wot.helper.R
import com.wot.helper.common.UiText

class ValidateEmail {

    operator fun invoke(email: String): ValidationResponse {
        if (email.isBlank()) {
            return ValidationResponse(
                isValid = false,
                errorMessage = UiText.StringResource(R.string.required)
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResponse(
                isValid = false,
                errorMessage = UiText.StringResource(R.string.invalid_email_error)
            )
        }
        return ValidationResponse(
            isValid = true
        )

    }
}