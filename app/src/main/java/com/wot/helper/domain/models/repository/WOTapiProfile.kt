package com.wot.helper.domain.models.repository

import com.wot.helper.domain.models.models.profileinfo.ProfileInfoRequest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WOTapiProfile {

    @GET("/wot/account/info/")
    suspend fun getInfoProfile(
        @Query("application_id") applicationId: String,
        @Query("account_id") accountId: String
    ): Response<ProfileInfoRequest>
}