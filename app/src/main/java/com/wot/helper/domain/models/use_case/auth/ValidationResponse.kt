package com.wot.helper.domain.models.use_case.auth

import com.wot.helper.R
import com.wot.helper.common.UiText

data class ValidationResponse(
    val isValid: Boolean,
    val errorMessage: UiText.StringResource? = UiText.StringResource(R.string.null_string)
)