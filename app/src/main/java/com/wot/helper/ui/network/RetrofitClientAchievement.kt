package com.wot.helper.ui.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.wot.helper.ui.network.WoTApiService // Explicitly import the correct WoTApiService

object RetrofitClientAchievement {
    private const val BASE_URL = "https://api.worldoftanks.eu/" // Ensure this is correct

    val api: WoTApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(com.wot.helper.ui.network.WoTApiService::class.java) // Explicitly reference the correct interface
    }
}
