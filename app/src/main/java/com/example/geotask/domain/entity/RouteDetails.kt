package com.example.geotask.domain.entity


data class RouteDetails(
        val end_address:String,
        val end_location: EndLocation,
        val start_address:String,
        val start_location: StartLocation,
        val steps:List<Location>
)

data class EndLocation(
        val lat: Double,
        val lng: Double
)

data class StartLocation(
        val lat: Double,
        val lng: Double
)
