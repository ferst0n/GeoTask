package com.example.geotask.presentation.routeBuilding

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.geotask.R
import com.example.geotask.presentation.PassDataBetweenFragmentsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RouteBuildingFragment() : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener{


    private lateinit var mMap: GoogleMap
    private val routeBuildingViewModel:RouteBuildingViewModel by viewModels()
    private val passDataBetweenFragmentsViewModel: PassDataBetweenFragmentsViewModel by activityViewModels()

    private lateinit var foundUnknownTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        var origin = passDataBetweenFragmentsViewModel.getIdOrigin()
        var destination = passDataBetweenFragmentsViewModel.getIdDestination()

        getPathCoordinates(origin!!, destination!!)

    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_route_building, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        foundUnknownTextView = view.findViewById(R.id.found_unknown_textview)



        return view
    }



    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        pathBuilding()

        if (ActivityCompat.checkSelfPermission(
                        activity?.applicationContext!!,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        activity?.applicationContext!!,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        mMap.isMyLocationEnabled = true
        mMap.setOnMyLocationButtonClickListener(this)
        mMap.setOnMyLocationClickListener(this)

    }

    private fun pathBuilding(){
        routeBuildingViewModel.routeDetails.observe(viewLifecycleOwner){
            val routes = it

            if (routes.isEmpty()){
                foundUnknownTextView.text = "Unknown"
            }else{
                foundUnknownTextView.text = "Found"

                for(route in routes){

                    var startLocation: LatLng = LatLng(route.start_location.lat,route.start_location.lng)
                    var startAddress:String = route.start_address
                    var endLocation: LatLng = LatLng(route.end_location.lat,route.end_location.lng)
                    var endAddress :String = route.end_address
                    var points : List<com.example.geotask.domain.entity.Location> = route.steps


                    val originMarkerOptions =
                            MarkerOptions()
                                    .title(startAddress)
                                    .position(startLocation)

                    val destinationMarkerOptions =
                            MarkerOptions()
                                    .title(endAddress)
                                    .position(endLocation)

                    mMap.addMarker(originMarkerOptions)
                    mMap.addMarker(destinationMarkerOptions)

                    val polyLineOptions = PolylineOptions()
                            .geodesic(true)
                            .color(Color.BLACK)
                            .width(15F)

                    for (i in 0 until points.size){
                        polyLineOptions.add(LatLng(points[i].lat,points[i].lng))
                    }
                    mMap.addPolyline(polyLineOptions)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(startLocation, 10F))
                }

            }

        }
    }


    private fun getPathCoordinates(origin: String, destination: String){
        lifecycleScope.launch {
            routeBuildingViewModel.getPathCoordinates(origin, destination)
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(activity?.applicationContext, "MyLocation button clicked", Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onMyLocationClick(p0: Location) {
        Toast.makeText(activity?.applicationContext, "Current location:\n$p0", Toast.LENGTH_LONG).show()
    }

}