package com.example.pennlabsandroidchallenge.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pennlabsandroidchallenge.R
import com.example.pennlabsandroidchallenge.objects.Restaurant
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity() {

    lateinit var mapFragment : SupportMapFragment
    lateinit var googleMap : GoogleMap
    lateinit var restaurants : ArrayList<Restaurant>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        restaurants = intent.getSerializableExtra("restaurants") as ArrayList<Restaurant>

        mapFragment = supportFragmentManager.findFragmentById(R.id.food_map) as SupportMapFragment
        mapFragment.getMapAsync (OnMapReadyCallback {
            googleMap = it
            googleMap.isMyLocationEnabled = true

            for (r in restaurants) {
                val latlng = LatLng(r.coordinates.first, r.coordinates.second)
                val restaurantName = r.name
                val markerOptions = MarkerOptions().position(latlng).title(restaurantName)
                googleMap.addMarker(markerOptions)
            }

            //setting the initial camera zoom coordinates to Ali Baba's coordinates
            val aliBaba = restaurants[restaurants.size-1]
            val initialCoordinates = LatLng(aliBaba.coordinates.first, aliBaba.coordinates.second)
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(initialCoordinates, 15f))

        })

    }
}
