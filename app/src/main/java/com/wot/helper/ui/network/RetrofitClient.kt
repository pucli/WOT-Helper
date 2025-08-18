package com.wot.helper.ui.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val api: WoTApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.worldoftanks.eu")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WoTApiService::class.java)
    }
}
