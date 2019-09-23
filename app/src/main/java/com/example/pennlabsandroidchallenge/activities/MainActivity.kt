package com.example.pennlabsandroidchallenge.activities

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.pennlabsandroidchallenge.R
import com.example.pennlabsandroidchallenge.adapters.ViewPagerAdapter
import com.example.pennlabsandroidchallenge.fragments.FoodFragment
import com.example.pennlabsandroidchallenge.fragments.OCRFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.appbar_main.*


class MainActivity : AppCompatActivity() {

    var viewPagerAdapter: ViewPagerAdapter?= null
    val MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    val MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(tabbar_main)

        /* Request for location permission if it's not already granted. The application current assumes
        that location permission WILL be granted. It does not account for permission denials */
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION
            )
        }

        viewPagerAdapter =
            ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter!!.addFragment(FoodFragment(), "Food")
        viewPagerAdapter!!.addFragment(OCRFragment(), "Buildings")

        view_pager.adapter = viewPagerAdapter
        tab_layout.setupWithViewPager(view_pager)
    }
}
