package com.example.pogodynka_vol_420

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.WrapperListAdapter
import androidx.fragment.app.Fragment
import com.astrocalculator.AstroCalculator
import com.astrocalculator.AstroDateTime
import com.survivingwithandroid.weatherapp.model.Weather
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FourthFragment : Fragment() {
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


        return inflater.inflate(R.layout.fragment_fourth, container, false)
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
            val miasto = view.findViewById<TextView>(R.id.miasto)
            val sila = view.findViewById<TextView>(R.id.sila)
            val kierunek = view.findViewById<TextView>(R.id.kierunek)
            val wilgotnosc = view.findViewById<TextView>(R.id.wilgotnosc)
            val widocznosc = view.findViewById<TextView>(R.id.widocznosc)

            miasto.text=weather.location.city.toString()
            sila.text=weather.wind.speed.toString()
            kierunek.text=weather.wind.deg.toString()
            wilgotnosc.text=weather.currentCondition.humidity.toString()
            widocznosc.text=weather.rain.ammount.toString()



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
                    val miasto = view.findViewById<TextView>(R.id.miasto)
                    val sila = view.findViewById<TextView>(R.id.sila)
                    val kierunek = view.findViewById<TextView>(R.id.kierunek)
                    val wilgotnosc = view.findViewById<TextView>(R.id.wilgotnosc)
                    val widocznosc = view.findViewById<TextView>(R.id.widocznosc)

                    miasto.text=weather.location.city.toString()
                    sila.text=weather.wind.speed.toString()
                    kierunek.text=weather.wind.deg.toString()
                    wilgotnosc.text=weather.currentCondition.humidity.toString()
                    widocznosc.text=weather.rain.ammount.toString()

                }


                refreshHandler.postDelayed(this, refresh.toLong() * 1000)
            }
        }
        refreshHandler.postDelayed(runnable, refresh.toLong() * 1000)
    }


}