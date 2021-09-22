package com.example.geotask.data.model

data class PlaceDetails(
        val result: result
)
data class result(
        val geometry:Geometry
)
data class Geometry(
        val location:Location
)
data class Location(
        val lat:Double,
        val lng:Double
)