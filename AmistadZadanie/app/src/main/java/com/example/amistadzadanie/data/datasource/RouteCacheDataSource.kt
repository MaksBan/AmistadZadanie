package com.example.amistadzadanie.data.datasource

import com.example.amistadzadanie.data.model.Route

class RouteCacheDataSource {
    var mapOfIdToRoute = mapOf<Int, Route>()

    suspend fun getRoutes(): Collection<Route>{
        return mapOfIdToRoute.values
    }

    suspend fun saveRoutesToCache(routes: List<Route>) {
        mapOfIdToRoute = routes.associateBy {
            it.id
        }

    }

    suspend fun getRouteById(routeId: Int): Route? {
        return mapOfIdToRoute[routeId]

    }

}
