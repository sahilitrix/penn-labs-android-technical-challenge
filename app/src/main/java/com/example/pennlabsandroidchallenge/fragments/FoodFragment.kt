package com.example.pennlabsandroidchallenge.fragments


import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pennlabsandroidchallenge.adapters.FoodRecyclerViewAdapter
import com.example.pennlabsandroidchallenge.activities.MapActivity

import com.example.pennlabsandroidchallenge.R
import com.example.pennlabsandroidchallenge.objects.Restaurant
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_food.*

/**
 * A simple [Fragment] subclass.
 */
class FoodFragment : Fragment() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var myLatitude : Double? = null
    private var myLongitude : Double? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context as Context)
        obtainLocation()
    }

    private fun obtainLocation()  {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                 myLatitude =  location?.latitude
                 myLongitude = location?.longitude
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val restaurants = ArrayList<Restaurant>()
        restaurants.add(
            Restaurant(
                "Mikey's Grill",
                "34th and Market",
                Pair(39.955854, -75.191432),
                "American, Sandwiches",
                5.0,
                null
            )
        )
        restaurants.add(
            Restaurant(
                "Lyn's",
                "36th and Spruce",
                Pair(39.950792, -75.195304),
                "American, Sandwiches",
                4.5,
                "6:00am-3:00pm (MF)"
            )
        )
        restaurants.add(
            Restaurant(
                "John's Lunch Cart",
                "33rd and Spruce",
                Pair(39.950331, -75.191717),
                "American, Sandwiches",
                4.5,
                null
            )
        )
        restaurants.add(
            Restaurant(
                "Rami's",
                "40th and Locust",
                Pair(39.952977, -75.202820),
                "Middle Eastern",
                4.5,
                null
            )
        )
        restaurants.add(
            Restaurant(
                "Sandwich Cart at 35th",
                "35th and Market",
                Pair(39.956213, -75.193475),
                "American, Sandwiches",
                4.5,
                null
            )
        )
        restaurants.add(
            Restaurant(
                "Gul's Breakfast and Lunch Cart",
                "36th and Market",
                Pair(39.956191, -75.194148),
                "American, Sandwiches",
                4.5,
                null
            )
        )
        restaurants.add(
            Restaurant(
                "Pete's Little Lunch Box",
                "33rd and Lancaster",
                Pair(39.956425, -75.189327),
                "American, Sandwiches",
                4.5,
                "6:00am-4:00pm (MF), 6:00am-4:00pm (Sa)"
            )
        )
        restaurants.add(
            Restaurant(
                "Magic Carpet at 36th",
                "36th and Spruce",
                Pair(39.950792, -75.195304),
                "Vegetarian",
                4.5,
                "11:30am-3:00pm (MF)"
            )
        )
        restaurants.add(
            Restaurant(
                "Troy Mediterranean at 38th",
                "38th and Spruce",
                Pair(39.951287, -75.199274),
                "Middle Eastern",
                4.5,
                null
            )
        )
        restaurants.add(
            Restaurant(
                "Fruit Truck at 37th",
                "37th and Spruce",
                Pair(39.951020, -75.197285),
                "Fruit",
                4.5,
                "11:30am-6:30pm (MF)"
            )
        )
        restaurants.add(
            Restaurant(
                "Memo's Lunch Truck",
                "33rd and Arch",
                Pair(39.957611, -75.189076),
                "Middle Eastern",
                4.5,
                "12:00am-12:00pm (MF)"
            )
        )
        restaurants.add(
            Restaurant(
                "Hanan House of Pita",
                "38th and Walnut",
                Pair(39.953640, -75.198767),
                "Middle Eastern",
                4.0,
                "11:00am-8:00pm (MF), 11:00am-8:00pm (Sa)"
            )
        )
        restaurants.add(
            Restaurant(
                "Fruit Truck at 35th",
                "35th and Market",
                Pair(39.956213, -75.193475),
                "Fruit",
                4.0,
                null
            )
        )
        restaurants.add(
            Restaurant(
                "Troy Mediterranean at 40th",
                "40th and Spruce",
                Pair(39.951756, -75.203074),
                "Middle Eastern",
                4.0,
                null
            )
        )
        restaurants.add(
            Restaurant(
                "Sonics",
                "37th and Spruce",
                Pair(39.951030, -75.197268),
                "American, Sandwiches",
                4.0,
                "8:30am-5:30pm (MF)"
            )
        )
        restaurants.add(
            Restaurant(
                "Ali Baba",
                "37th and Walnut",
                Pair(39.953388, -75.196763),
                "Middle Eastern",
                4.0,
                "8:00am-4:00pm (MF)"
            )
        )


        food_recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        food_recycler_view.adapter =
            FoodRecyclerViewAdapter(restaurants)

        sort_by_toggle.setOnCheckedChangeListener { buttonView, isChecked ->
            val adapter : FoodRecyclerViewAdapter = food_recycler_view.adapter as FoodRecyclerViewAdapter
            //checked state = sort by distance, unchecked state = sort by rating
            if (isChecked) {
                adapter.sortListBy("distance", Pair(myLatitude, myLongitude))
            }
            else
                adapter.sortListBy("rating", null)
            food_recycler_view.adapter = adapter
            food_recycler_view?.adapter?.notifyDataSetChanged()

        }
        food_recycler_view?.adapter?.notifyDataSetChanged()

        map_button.setOnClickListener {
            val intent = Intent(context, MapActivity::class.java)
            intent.putExtra("restaurants", restaurants)
            startActivity(intent)
        }




    }


}
