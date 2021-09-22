package com.example.geotask.domain.repository

import com.example.geotask.domain.entity.Location
import com.example.geotask.domain.entity.PlaceName
import com.example.geotask.domain.entity.RouteDetails

interface PlaceRepository {

    suspend fun getPredictionsPlace(enteredPlace:String): List<PlaceName>?
    suspend fun getPathCoordinates(origin:String,destination:String): List<RouteDetails>?
    suspend fun getPlaceDetails(placeId:String): Location?
}