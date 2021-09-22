package com.example.geotask.data.mappers

import com.example.geotask.data.Utils
import com.example.geotask.data.model.PathCoordinates
import com.example.geotask.data.model.PlaceDetails
import com.example.geotask.data.model.Predictions
import com.example.geotask.domain.entity.*
import javax.inject.Inject

class FromDataToDomainMapper @Inject constructor(){

    fun fromDataToDomainPredictionsPlace(predictions: Predictions): List<PlaceName> {

        return predictions.predictions.map{
            PlaceName(it.description, it.place_id)
        }

    }

    fun fromDataToDomainPlaceDetails(placeDetails: PlaceDetails): Location {
        return Location(placeDetails.result.geometry.location.lat, placeDetails.result.geometry.location.lng)

    }

    fun fromDataToDomainPathCoordinates(pathCoordinates: PathCoordinates): List<RouteDetails> {
        var routes = pathCoordinates.routes
        var routeDetailsList = ArrayList<RouteDetails>()

        for(i in 0 until routes!!.size){

            var legs = routes[i].legs
            var steps = Utils.decodePolyLine(routes[i].overview_polyline.points)!!
            var location = ArrayList<Location>()

            for(j in 0 until legs.size){

                var end_address = legs[j].end_address
                var end_location =  EndLocation(legs[j].end_location.lat,legs[j].end_location.lng)
                var start_address = legs[j].start_address
                var start_location = StartLocation(legs[j].start_location.lat,legs[j].start_location.lng)

                for (g in 0 until steps.size){

                    location.add(Location(steps[g].latitude,steps[g].longitude))

                }


                routeDetailsList.add(RouteDetails(end_address, end_location, start_address, start_location, location))

            }

        }
        return routeDetailsList
    }
}