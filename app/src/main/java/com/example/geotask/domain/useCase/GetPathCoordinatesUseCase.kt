package com.example.geotask.domain.useCase

import com.example.geotask.domain.entity.RouteDetails
import com.example.geotask.domain.repository.PlaceRepository
import javax.inject.Inject

class GetPathCoordinatesUseCase @Inject constructor(private val placeRepository: PlaceRepository) {

    suspend fun execute(origin:String,destination:String): List<RouteDetails>? {
        return placeRepository.getPathCoordinates(origin,destination)
    }
}