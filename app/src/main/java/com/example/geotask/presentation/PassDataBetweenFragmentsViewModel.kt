package com.example.geotask.presentation

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PassDataBetweenFragmentsViewModel@Inject constructor():ViewModel() {



    val destinationId:MutableLiveData<String>? = MutableLiveData<String>("")
    val originId:MutableLiveData<String>? = MutableLiveData<String>("")

    fun setDestinationId(placeId:String){
        destinationId?.value = placeId
    }

    fun getIdDestination() = destinationId?.value


    fun setOriginId(placeId:String){
        originId?.value = placeId

    }

    fun  getIdOrigin() = originId?.value

}