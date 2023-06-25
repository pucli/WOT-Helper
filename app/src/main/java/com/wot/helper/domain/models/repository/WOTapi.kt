package com.wot.helper.domain.models.repository


import com.wot.helper.domain.models.models.tanksinfo.TankInfoRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WOTapi {

     @GET("/wot/encyclopedia/vehicles/?application_id=ace14516f4be72cde04425adca560339")
     suspend fun getInfo(): Response<TankInfoRequest>
 }