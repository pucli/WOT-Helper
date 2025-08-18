package com.wot.helper.ui.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientSkills {
    private const val BASE_URL = "https://api.worldoftanks.eu/"

    val api: WoTSkillsApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(com.wot.helper.ui.network.WoTSkillsApiService::class.java)
    }
}
