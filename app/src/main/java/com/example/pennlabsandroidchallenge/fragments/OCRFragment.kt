package com.example.pennlabsandroidchallenge.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.pennlabsandroidchallenge.R
import com.example.pennlabsandroidchallenge.activities.BuildingDetailActivity
import com.example.pennlabsandroidchallenge.objects.Building
import kotlinx.android.synthetic.main.fragment_ocr.*
import okhttp3.*
import java.io.IOException
import org.json.JSONObject
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.KeyEvent


/**
 * A simple [Fragment] subclass.
 */
class OCRFragment : Fragment() {

    private val BUIDLING_API_ENDPOINT = "https://api.pennlabs.org/buildings/search?q=hill"
    //using Square Up's OkHttp client to make api call
    private val httpclient = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ocr, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        submit_button.setOnClickListener {
            val searchString = search_field.text.toString()
            makeBuildingAPIRequest(searchString)
        }

        //override enter key to register as submit
        search_field.setOnKeyListener { v, keyCode, event ->
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KEYCODE_ENTER)) {
                val searchString = search_field.text.toString()
                makeBuildingAPIRequest(searchString)
                true
            }
            false
        }
    }

    fun makeBuildingAPIRequest(search : String) {
        val request = Request.Builder()
            .url(BUIDLING_API_ENDPOINT)
            .build()

        httpclient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("API CALL", e.localizedMessage)
            }
            override fun onResponse(call: Call, response: Response) {
                val jsonData = response.body()!!.string()
                val jObject = JSONObject(jsonData)
                val jBuildingArray = jObject.getJSONArray("result_data")

                Log.d("API CALL", jsonData)

                for (i in 0 until jBuildingArray.length()){
                    val jBuilding = jBuildingArray.getJSONObject(i)
                    val keywords = jBuilding.getString("keywords").toLowerCase()
                    val name = jBuilding.getString("title")
                    if (keywords.contains(search.toLowerCase()) || name.toLowerCase().contains(search.toLowerCase())) {
                        val location = jBuilding.getString("address")
                        val coordinates = Pair(jBuilding.getString("latitude").toDouble(), jBuilding.getString("longitude").toDouble())
                        val floors = jBuilding.getString("floors")
                        val year = jBuilding.getString("year_built")

                        val building = Building(name, location, coordinates, floors, year)
                        openBuildingDetailActivity(building)
                        break
                    }
                }
            }
        })
    }

    fun openBuildingDetailActivity(building : Building){
        val intent = Intent(context, BuildingDetailActivity::class.java)
        intent.putExtra("building", building)
        startActivity(intent)
    }
}
