package com.example.pennlabsandroidchallenge.objects

import java.io.Serializable


data class Building (val name: String?, val address: String?, val coordinates: Pair<Double, Double>, val floors: String?, val year : String?) : Serializable