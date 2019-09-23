package com.example.pennlabsandroidchallenge.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.pennlabsandroidchallenge.R
import com.example.pennlabsandroidchallenge.objects.Restaurant
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_restaurant_detail.*

class RestaurantDetailActivity : AppCompatActivity() {

    private lateinit var restaurant : Restaurant
    private lateinit var googleMap : GoogleMap
    private lateinit var miniMapFragment : SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_detail)

        restaurant = intent.getSerializableExtra("restaurant") as Restaurant

        setLabels()
        setStars()
        setMap()
    }

    fun setLabels(){
        restaurant_name_textview.text = restaurant.name
        restaurant_location_textview.text = restaurant.address
        restaurant_timings_textview.text = restaurant.timings ?: "Timings Not Available"
        restaurant_type_textview.text = restaurant.type
    }

    fun setStars(){
        //translate the rating into star icons
        val numFullStars = Math.floor(restaurant.rating).toInt()
        val numHalfStars = if (restaurant.rating - numFullStars >= 0.5) 1 else 0
        val numEmptyStars = 5 - numFullStars - numHalfStars

        val ratingStars = arrayOf(star1, star2, star3, star4, star5)

        for (i in 0 until numFullStars)
            ratingStars[i].setImageResource(R.drawable.full_star_icon)
        for (i in 0 until numHalfStars)
            ratingStars[numFullStars+i].setImageResource(R.drawable.half_star_icon)
        for (i in 0 until numEmptyStars)
            ratingStars[numFullStars+numHalfStars+i].setImageResource(R.drawable.empty_star_icon)
    }

    fun setMap(){
        miniMapFragment = supportFragmentManager.findFragmentById(R.id.food_map) as SupportMapFragment
        miniMapFragment.getMapAsync (OnMapReadyCallback {
            googleMap = it
            googleMap.isMyLocationEnabled = true

            val latlng = LatLng(restaurant.coordinates.first, restaurant.coordinates.second)
            val restaurantName = restaurant.name
            val markerOptions = MarkerOptions().position(latlng).title(restaurantName)
            googleMap.addMarker(markerOptions)
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 16f))
        })
    }

}
