package com.example.pennlabsandroidchallenge.objects

import java.io.Serializable


data class Restaurant (val name: String, val address: String, val coordinates: Pair<Double, Double>, val type: String, val rating : Double, val timings : String?) : Serializable