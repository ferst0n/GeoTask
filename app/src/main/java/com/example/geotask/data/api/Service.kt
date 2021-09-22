package com.example.geotask.data.api

import com.example.geotask.data.model.PathCoordinates
import com.example.geotask.data.model.Predictions
import com.example.geotask.data.model.PlaceDetails
import retrofit2.Response
import retrofit2.http.GET

import retrofit2.http.Query


interface Service {


    @GET("place/autocomplete/json")
    suspend fun getPredictions(@Query("input")input:String,
                               @Query("types")types:String,
                               @Query("key")key:String

    ):Response<Predictions>

    @GET("directions/json")
    suspend fun getPathCoordinates(@Query("origin")origin:String,
                                   @Query("destination")destination:String,
                                   @Query("key")key:String

    ):Response<PathCoordinates>

    @GET("place/details/json")
    suspend fun getPlaceDetails(@Query("place_id")id:String,
                                @Query("key")key:String

    ):Response<PlaceDetails>
}