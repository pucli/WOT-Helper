package com.wot.helper.domain.models.use_case.auth

import com.wot.helper.R
import com.wot.helper.common.UiText

class ValidateUsername {

    operator fun invoke(username: String): ValidationResponse {
        if (username.isBlank()) {
            return ValidationResponse(
                isValid = false,
                errorMessage = UiText.StringResource(R.string.required)
            )
        }
        return ValidationResponse(
            isValid = true
        )
    }
}