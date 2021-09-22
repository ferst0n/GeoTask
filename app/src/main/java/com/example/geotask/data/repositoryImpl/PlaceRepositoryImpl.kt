package com.example.geotask.data.repositoryImpl

import com.example.geotask.data.DataSource.RemoteDataSource
import com.example.geotask.data.Utils
import com.example.geotask.data.mappers.FromDataToDomainMapper
import com.example.geotask.data.model.PathCoordinates
import com.example.geotask.data.model.PlaceDetails
import com.example.geotask.data.model.Predictions
import com.example.geotask.domain.entity.*
import com.example.geotask.domain.repository.PlaceRepository
import com.google.android.gms.maps.model.LatLng
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class PlaceRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource,
                                              private val mapper:FromDataToDomainMapper):PlaceRepository{

    override suspend fun getPredictionsPlace(enteredPlace: String): List<PlaceName>? {
        val result = remoteDataSource.getPredictions(enteredPlace)
        return mapper.fromDataToDomainPredictionsPlace(result)
    }

    override suspend fun getPathCoordinates(origin: String, destination: String): List<RouteDetails>?{
        val result = remoteDataSource.getPathCoordinates(origin, destination)
        return mapper.fromDataToDomainPathCoordinates(result)
    }

    override suspend fun getPlaceDetails(placeId: String): Location? {
        val result = remoteDataSource.getPlaceDetails(placeId)
        return mapper.fromDataToDomainPlaceDetails(result)
    }

}