package com.wot.helper.domain.models.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstanceProfile {

    val api: WOTapiProfile by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.worldoftanks.eu")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WOTapiProfile::class.java)
    }
}