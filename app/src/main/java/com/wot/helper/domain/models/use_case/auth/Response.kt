package com.wot.helper.domain.models.use_case.auth

sealed class Response<out T> {

    data class Success<out T>(
        val data: T
    ) : Response<T>()

    data class Failure(
        val errorMessage: String
    ) : Response<Nothing>()
}