package com.example.geotask.domain.useCase

import com.example.geotask.domain.repository.PlaceRepository
import javax.inject.Inject


class GetPlaceNamePredictsUseCase @Inject constructor (private val placeRepository: PlaceRepository) {

    suspend fun execute(enteredPlace: String) = placeRepository.getPredictionsPlace(enteredPlace)




}