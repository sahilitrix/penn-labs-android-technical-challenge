package com.example.pennlabsandroidchallenge.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pennlabsandroidchallenge.R
import com.example.pennlabsandroidchallenge.objects.Building
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_building_detail.*
import kotlinx.android.synthetic.main.activity_restaurant_detail.*

class BuildingDetailActivity : AppCompatActivity() {

    private lateinit var building : Building
    private lateinit var googleMap : GoogleMap
    private lateinit var miniMapFragment : SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_building_detail)

        building = intent.getSerializableExtra("building") as Building

        setLabels()
        setMap()
    }

    fun setLabels(){
        building_name_textview.text = building.name
        building_location_textview.text = "Address: " +building.address
        building_floors_textview.text = "# Floors: " +building.floors
        building_year_textview.text = "Year Built: " +building.year
    }

    fun setMap(){
        miniMapFragment = supportFragmentManager.findFragmentById(R.id.building_map) as SupportMapFragment
        miniMapFragment.getMapAsync (OnMapReadyCallback {
            googleMap = it
            googleMap.isMyLocationEnabled = true

            val latlng = LatLng(building.coordinates.first, building.coordinates.second)
            val buildingName = building.name
            val markerOptions = MarkerOptions().position(latlng).title(buildingName)
            googleMap.addMarker(markerOptions)
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 16f))
        })
    }

}
