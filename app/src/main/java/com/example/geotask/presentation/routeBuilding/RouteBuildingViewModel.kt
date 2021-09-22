package com.example.geotask.presentation.routeBuilding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geotask.data.model.PathCoordinates
import com.example.geotask.domain.entity.RouteDetails
import com.example.geotask.domain.useCase.GetPathCoordinatesUseCase
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouteBuildingViewModel @Inject constructor(private val getPathCoordinatesUseCase: GetPathCoordinatesUseCase): ViewModel() {


    private val _routeDetails = MutableLiveData<List<RouteDetails>>()
    var routeDetails = _routeDetails


    suspend fun getPathCoordinates(origin:String, destination:String){

        viewModelScope.launch {
            val data = getPathCoordinatesUseCase.execute(origin,destination)
            routeDetails.value = data

        }
    }

    private val originMarkerOptions = MutableLiveData<MarkerOptions>()
    private val destinationMarkerOptions = MutableLiveData<MarkerOptions>()
    private val polyLineOptions = MutableLiveData<PolylineOptions>()

    fun setOriginMarkerOptions(markerOptions: MarkerOptions){
        originMarkerOptions.value = markerOptions
    }
    fun getOriginMarkerOptions() = originMarkerOptions.value

    fun setDestinationMarkerOptions(markerOptions: MarkerOptions){
        destinationMarkerOptions.value = markerOptions
    }
    fun getDestinationMarkerOptions() = destinationMarkerOptions.value

    fun setPolyLineOptions(polylineOptions: PolylineOptions){
        polyLineOptions.value = polylineOptions
    }
    fun getPolyLineOptions() = polyLineOptions.value

}