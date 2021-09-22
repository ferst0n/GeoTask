package com.example.geotask.data.DataSource

import android.util.Log
import com.example.geotask.BuildConfig
import com.example.geotask.data.api.Service
import com.example.geotask.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val service: Service){


    suspend fun getPredictions(enteredPlace:String):Predictions =

            withContext(Dispatchers.IO) {
                val response = service.getPredictions(enteredPlace, "geocode","${
                    BuildConfig.GOOGLE_API_KEY}")

                if (response.isSuccessful) {
                    Log.d("getPredictions", response.body().toString())

                    return@withContext response.body()!!
                }else{
                    return@withContext Predictions((listOf(Place("Не найдено","0"))),"unknown")
                }

            }

    suspend fun getPathCoordinates(originId:String,destinationId:String): PathCoordinates{
        return withContext(Dispatchers.IO) {
            val origin = "place_id:"+originId
            val destination = "place_id:"+destinationId
            val response = service.getPathCoordinates(origin, destination,"${
                BuildConfig.GOOGLE_API_KEY}")

            if (response.isSuccessful) {
                Log.d("getPathCoordinates", response.body().toString())

                return@withContext response.body()!!
            }else{
                return@withContext PathCoordinates(emptyList(), emptyList(),"unknown")
            }

        }
    }

    suspend fun getPlaceDetails(placeId:String): PlaceDetails{
        return withContext(Dispatchers.IO) {
            val response = service.getPlaceDetails(placeId,"${BuildConfig.GOOGLE_API_KEY}")

            if (response.isSuccessful) {
                Log.d("getPlaceDetails", response.body().toString())

                return@withContext response.body()!!
            }else{
                return@withContext PlaceDetails(result(Geometry(Location(0.0,0.0))))
            }

        }
    }




}