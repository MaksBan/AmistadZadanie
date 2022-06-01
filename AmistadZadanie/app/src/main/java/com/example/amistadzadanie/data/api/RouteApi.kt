package com.example.amistadzadanie.data.api

import com.example.amistadzadanie.data.model.RouteList
import retrofit2.Response
import retrofit2.http.GET

interface RouteApi {
    @GET("/v2/route/index?track=1")
        suspend fun getRoutes(): Response<RouteList>
}