package com.wot.helper.ui.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientMission {

    private const val BASE_URL = "https://api.worldoftanks.eu/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val missionsApi: MissionsApi by lazy {
        retrofit.create(MissionsApi::class.java)
    }
}
