package com.wot.helper.domain.models.repository


import com.wot.helper.domain.models.models.profileinfo.ProfileInfoRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface WOTapiProfile {

    @GET("/wot/account/info/?application_id=ace14516f4be72cde04425adca560339&account_id=533694329")
    suspend fun getInfoProfile(): Response<ProfileInfoRequest>
}