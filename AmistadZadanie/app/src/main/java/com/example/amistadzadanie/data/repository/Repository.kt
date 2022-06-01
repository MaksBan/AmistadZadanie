package com.example.amistadzadanie.data.repository

import android.util.Log
import com.example.amistadzadanie.data.api.RouteApi
import com.example.amistadzadanie.data.datasource.RouteCacheDataSource
import com.example.amistadzadanie.data.model.Route

class Repository(
    private val routeApi: RouteApi,
    private val routeCacheDataSource: RouteCacheDataSource
) {

    suspend fun getRoutes(): List<Route> {
        return if (routeCacheDataSource.mapOfIdToRoute.isNotEmpty()) {
            println("Data from Cache")
            routeCacheDataSource.getRoutes().toList()
        } else {
            val routeList = routeApi.getRoutes().body()!!
            val routes = routeList.routes
            routeCacheDataSource.saveRoutesToCache(routes)
            println("Data from Api")
            routes

        }
    }

    suspend fun getRouteById(routeId: Int): Route? {
        return  routeCacheDataSource.getRouteById(routeId)
    }
}
