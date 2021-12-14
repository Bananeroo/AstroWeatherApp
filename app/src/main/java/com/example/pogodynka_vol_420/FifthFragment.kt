package com.example.pogodynka_vol_420

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.WrapperListAdapter
import androidx.fragment.app.Fragment
import com.astrocalculator.AstroCalculator
import com.astrocalculator.AstroDateTime
import com.survivingwithandroid.weatherapp.model.Weather
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FifthFragment : Fragment() {
    var longitude: Double=-1.0;
    var latitude: Double=-1.0;
    var refresh: Int=1;
    var weather:Weather= Weather();
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        longitude = this.requireArguments().getDouble("longitude")
        latitude = this.requireArguments().getDouble("latitude")
        refresh = this.requireArguments().getInt("refresh")
        if(longitude!=-1.0)
            weather = this.requireArguments().getSerializable("klasa") as Weather


        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble("longitude", longitude)

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val now = LocalDateTime.now()


        val editText = view.findViewById<TextView>(R.id.czas)

        val formatter: DateTimeFormatter =
                DateTimeFormatter.ofPattern("HH:mm:ss")
        editText.text= now.format(formatter);

        if(longitude!=-1.0){
            val szerokosc = view.findViewById<TextView>(R.id.szerokosc)
            val dlugosc = view.findViewById<TextView>(R.id.dlugosc)
            val miasto = view.findViewById<TextView>(R.id.miasto)
            val temperatura = view.findViewById<TextView>(R.id.temp)
            val cisnienie = view.findViewById<TextView>(R.id.cisn)
            val opis = view.findViewById<TextView>(R.id.opis)
            val obrazek = view.findViewById<ImageView>(R.id.obrazek)

            val fileName = "logo.png"








        }



        val refreshHandler = Handler()
        val runnable: Runnable = object : Runnable {
            override fun run() {
                // do updates for imageview
                val now = LocalDateTime.now()

                val formatter: DateTimeFormatter =
                        DateTimeFormatter.ofPattern("HH:mm:ss")
                editText.text= now.format(formatter);
                ;
                if(longitude!=-1.0){
                    val szerokosc = view.findViewById<TextView>(R.id.szerokosc)
                    val dlugosc = view.findViewById<TextView>(R.id.dlugosc)
                    val miasto = view.findViewById<TextView>(R.id.miasto)
                    val temperatura = view.findViewById<TextView>(R.id.temp)
                    val cisnienie = view.findViewById<TextView>(R.id.cisn)
                    val opis = view.findViewById<TextView>(R.id.opis)
                    szerokosc.text=weather.location.latitude.toString()
                    dlugosc.text=weather.location.longitude.toString()
                    miasto.text=weather.location.city.toString()


                }


                refreshHandler.postDelayed(this, refresh.toLong() * 1000)
            }
        }
        refreshHandler.postDelayed(runnable, refresh.toLong() * 1000)
    }


}