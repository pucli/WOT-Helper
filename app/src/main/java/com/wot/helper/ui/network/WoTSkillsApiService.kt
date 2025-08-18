package com.wot.helper.ui.network

import com.wot.helper.ui.skills.model.SkillsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WoTSkillsApiService {
    @GET("wot/encyclopedia/crewskills/")
    suspend fun getSkills(
        @Query("application_id") appId: String
    ): Response<SkillsResponse>
}
