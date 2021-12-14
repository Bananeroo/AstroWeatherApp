package com.example.pogodynka_vol_420

import android.util.Log
import java.net.URL

class Request(private val url: String){

    fun run(){
        Log.d("XD",url)
        val forecastJson = URL(url).readText()
        Log.d("XD","twoja starsza")
        Log.d("onpoint",forecastJson)


    }
}