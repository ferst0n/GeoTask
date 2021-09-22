package com.example.geotask.domain.useCase

import com.example.geotask.domain.repository.PlaceRepository
import javax.inject.Inject

class GetPlaceDetailsUseCase@Inject constructor (private val placeRepository: PlaceRepository) {

    suspend fun execute(placeId: String) = placeRepository.getPlaceDetails(placeId)
}