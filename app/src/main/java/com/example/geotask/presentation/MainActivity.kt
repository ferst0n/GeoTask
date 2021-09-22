package com.example.geotask.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.geotask.BuildConfig
import com.example.geotask.R
import com.example.geotask.presentation.routeBuilding.RouteBuildingFragment
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.Theme_GEOTASK)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Places.initialize(applicationContext, "${BuildConfig.GOOGLE_API_KEY}")

    }



}

