package com.example.pennlabsandroidchallenge.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pennlabsandroidchallenge.R
import com.example.pennlabsandroidchallenge.activities.RestaurantDetailActivity
import com.example.pennlabsandroidchallenge.objects.Restaurant
import java.lang.Math.floor
import kotlin.coroutines.coroutineContext

class FoodRecyclerViewAdapter(restaurantList: ArrayList<Restaurant>) : RecyclerView.Adapter<FoodRecyclerViewAdapter.ViewHolder> () {

    var restaurantList : ArrayList<Restaurant> = restaurantList

     fun sortListBy(filter : String, currentCoordinates: Pair<Double?, Double?>?) {
        if (filter.equals("rating"))
            restaurantList = listByRating(restaurantList)
        else if (filter.equals("distance")) {
            val currentCoordinates = currentCoordinates as Pair<Double, Double>
            restaurantList = listByDistance(restaurantList, currentCoordinates)
        }
    }

    private fun listByRating(restaurants : ArrayList<Restaurant>) : ArrayList<Restaurant> {
        var list = ArrayList<Restaurant>()
        list.addAll(restaurants)
        list.sortWith(compareBy({ it.rating }))
        list.reverse()
        return list
    }

    private fun listByDistance(restaurants : ArrayList<Restaurant>, currentCoordinates: Pair<Double, Double>) : ArrayList<Restaurant> {
        var list = ArrayList<Restaurant>()
        list.addAll(restaurants)
        list.sortWith(compareBy({ distance(it.coordinates, currentCoordinates) }))
        return list
    }

    private fun distance (rCoordinates : Pair<Double, Double>, mCoordinates: Pair<Double, Double>) : Double {
        val x1 = rCoordinates.first
        val y1 = rCoordinates.second
        val x2 = mCoordinates.first
        val y2 = mCoordinates.second
        val dist = Math.sqrt(Math.pow((x1-x2), 2.0) + Math.pow((y1-y2), 2.0))
        return dist
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =  LayoutInflater.from(parent.context).inflate(R.layout.food_list_item_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant: Restaurant = restaurantList[position]

        //handle click events on list items
        holder.itemView.setOnClickListener{
            var intent = Intent(holder.itemView.context, RestaurantDetailActivity::class.java)
            intent.putExtra("restaurant", restaurant)
            holder.itemView.context.startActivity(intent)
        }

        holder.nameTextView.text = restaurant.name
        holder.addressTextView.text = restaurant.address
        holder.typeTextView.text = restaurant.type

        //translate the rating into star icons
        val numFullStars = floor(restaurant.rating).toInt()
        val numHalfStars = if (restaurant.rating - numFullStars >= 0.5) 1 else 0
        val numEmptyStars = 5 - numFullStars - numHalfStars

        for (i in 0 until numFullStars)
            holder.ratingStars[i].setImageResource(R.drawable.full_star_icon)
        for (i in 0 until numHalfStars)
            holder.ratingStars[numFullStars+i].setImageResource(R.drawable.half_star_icon)
        for (i in 0 until numEmptyStars)
            holder.ratingStars[numFullStars+numHalfStars+i].setImageResource(R.drawable.empty_star_icon)

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nameTextView = itemView.findViewById(R.id.restaurant_name_textview) as TextView
        val addressTextView = itemView.findViewById(R.id.restaurant_location_textview) as TextView
        val typeTextView = itemView.findViewById(R.id.restaurant_type_textview) as TextView

        val ratingStars = arrayOf(
             itemView.findViewById(R.id.star1) as ImageView,
             itemView.findViewById(R.id.star2) as ImageView,
             itemView.findViewById(R.id.star3) as ImageView,
             itemView.findViewById(R.id.star4) as ImageView,
             itemView.findViewById(R.id.star5) as ImageView)

    }

}