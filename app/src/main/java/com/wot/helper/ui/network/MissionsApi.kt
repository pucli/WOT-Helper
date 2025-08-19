package com.wot.helper.ui.network

import com.wot.helper.domain.models.missions.MissionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MissionsApi {
    @GET("wot/encyclopedia/personalmissions/")
    suspend fun getPersonalMissions(
        @Query("application_id") appId: String = "ace14516f4be72cde04425adca560339"
    ): MissionResponse
}
