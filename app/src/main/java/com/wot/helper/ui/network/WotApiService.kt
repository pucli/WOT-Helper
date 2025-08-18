package com.wot.helper.ui.network

import com.wot.helper.ui.achievements.model.AchievementsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WoTApiService {
    @GET("wot/encyclopedia/achievements/") // Ensure this matches the API endpoint
    suspend fun getAchievements(
        @Query("application_id") appId: String // Ensure this matches the API parameter
    ): Response<AchievementsResponse>
}
