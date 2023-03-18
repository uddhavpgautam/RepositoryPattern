package com.example.repositorypattern.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.repositorypattern.R
import com.example.repositorypattern.databinding.ActivityMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val raleigh = LatLng(35.7796, 78.6382)
        mMap.addMarker(MarkerOptions().position(raleigh).title("Marker in Raleigh NC"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(raleigh))
    }
}
