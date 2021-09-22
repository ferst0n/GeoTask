package com.example.geotask.data.model


data class Predictions(
        val predictions: List<Place>,
        val status: String
)

data class Place(
    val description: String,
    val place_id: String
)
