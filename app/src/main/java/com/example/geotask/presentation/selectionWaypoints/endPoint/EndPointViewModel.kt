package com.example.geotask.presentation.selectionWaypoints.endPoint

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geotask.domain.entity.Location
import com.example.geotask.domain.entity.PlaceName
import com.example.geotask.domain.useCase.GetPlaceDetailsUseCase
import com.example.geotask.domain.useCase.GetPlaceNamePredictsUseCase
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EndPointViewModel @Inject constructor(private val getPlaceNamePredictsUseCase: GetPlaceNamePredictsUseCase,
                                            private val getPlaceDetailsUseCase: GetPlaceDetailsUseCase): ViewModel() {


    private val _place = MutableLiveData<List<PlaceName>>()
    var place = _place

    private val _placeDetails = MutableLiveData<Location>()
    var placeDetails = _placeDetails


     suspend fun getPlaceNamePredicts(enteredPlace: String){
        viewModelScope.launch {

            val data = getPlaceNamePredictsUseCase.execute(enteredPlace)
            place.value = data

        }
    }

    suspend fun getPlaceDetails(placeId:String){
        viewModelScope.launch {
            val data = getPlaceDetailsUseCase.execute(placeId)
            placeDetails.value = data
        }
    }


    private val markerOptions = MutableLiveData<MarkerOptions>()

    fun setMarkerOptions(markerOptions:MarkerOptions){
        this.markerOptions.value = markerOptions
    }

    fun getMarkerOptions() = markerOptions.value
}