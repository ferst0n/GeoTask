package com.example.geotask.presentation.selectionWaypoints.startPoint

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.geotask.R
import com.example.geotask.domain.entity.Location
import com.example.geotask.domain.entity.PlaceName
import com.example.geotask.presentation.PassDataBetweenFragmentsViewModel
import com.example.geotask.presentation.selectionWaypoints.PlacePredictionArrayAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class StartPointFragment:Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val startPointViewModel: StartPointViewModel by viewModels()
    private lateinit var autoCompleteTextView:AutoCompleteTextView
    private  var mContext: Context? = null
    private lateinit var placePredictionArrayAdapter: PlacePredictionArrayAdapter
    private val passDataBetweenFragmentsViewModel: PassDataBetweenFragmentsViewModel by activityViewModels()



    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context

    }


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_start_point, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        autoCompleteTextView = view.findViewById(R.id.autoCTV)
        autoCompleteTextView.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                var enteredPlace = s.toString()
                getPlaceNamePredicts(enteredPlace)

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        placePredictionArrayAdapter = PlacePredictionArrayAdapter(activity!!, R.layout.item, autoCompleteTextView)

        autoCompleteTextView.threshold = 2
        autoCompleteTextView.setAdapter(placePredictionArrayAdapter)

        startPointViewModel.place.observe(viewLifecycleOwner, {
            placePredictionArrayAdapter.setPlace(it)
        })


        autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            val selectedPlace = parent.adapter.getItem(position) as PlaceName

            val selectedPlaceId = selectedPlace.id
            val selectedPlaceName = selectedPlace.name

            autoCompleteTextView.setText(selectedPlaceName)
            passDataBetweenFragmentsViewModel.setOriginId(selectedPlaceId!!)

            getPlaceDetails(selectedPlaceId!!)
            startPointViewModel.placeDetails.observe(viewLifecycleOwner){

                setMarker(it,selectedPlaceName)

            }

        }

        return view
    }

    private fun setMarker(location: Location, selectedPlaceName:String){

        val locationDetails = LatLng(location.lat, location.lng)

        mMap.clear()

        val markerOptions = MarkerOptions()
                .position(locationDetails)
                .title(selectedPlaceName)

        mMap.addMarker(markerOptions)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationDetails, 13F))

        startPointViewModel.setMarkerOptions(markerOptions)
    }


    private fun getPlaceNamePredicts(enteredPlace: String) {
        lifecycleScope.launch {
            startPointViewModel.getPlaceNamePredicts(enteredPlace)
        }
    }

    private fun getPlaceDetails(placeId: String){
        lifecycleScope.launch {
            startPointViewModel.getPlaceDetails(placeId)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (startPointViewModel.getMarkerOptions() != null){
            var markerOptions = startPointViewModel.getMarkerOptions()
            mMap.addMarker(markerOptions)
        }

    }


}

