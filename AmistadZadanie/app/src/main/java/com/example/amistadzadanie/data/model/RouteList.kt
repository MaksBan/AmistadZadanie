package com.example.amistadzadanie.data.model


import com.google.gson.annotations.SerializedName

data class RouteList(
    @SerializedName("routes")
    val routes: List<Route>
)