package com.wot.helper.domain.models.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: WOTapi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.worldoftanks.eu")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WOTapi::class.java)
    }
}