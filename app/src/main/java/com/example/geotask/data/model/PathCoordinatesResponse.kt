package com.example.geotask.data.model


data class PathCoordinates(
        val geocoded_waypoints: List<Waypoints>,
        val routes: List<Routes>,
        val status: String
)

data class Waypoints(
        val geocoder_status:String,
        val place_id:String,
        val types:List<String>?
)

data class Routes(
        val legs:List<Legs>,
        val overview_polyline:Points
)

data class Points(
        val points: String
)

data class Legs(
        val end_address:String,
        val end_location:EndLocation,
        val start_address:String,
        val start_location:StartLocation,
        val steps:List<Steps>
)

data class Steps(
        val end_location:EndLocation,
        val polyline:Points
)

data class EndLocation(
        val lat: Double,
        val lng: Double
)

data class StartLocation(
        val lat: Double,
        val lng: Double
)