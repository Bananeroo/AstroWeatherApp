package com.example.pogodynka_vol_420

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.astrocalculator.AstroCalculator
import com.astrocalculator.AstroDateTime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    var longitude: Double=-1.0;
    var latitude: Double=-1.0;
    var refresh: Int=1;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        longitude = this.requireArguments().getDouble("longitude")
        latitude = this.requireArguments().getDouble("latitude")
        refresh = this.requireArguments().getInt("refresh")



        return inflater.inflate(R.layout.fragment_second, container, false)
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
            val datatime=AstroDateTime(now.year,now.monthValue,now.dayOfMonth,now.hour,now.minute,now.second,1,true);
            val astro= AstroCalculator(datatime,AstroCalculator.Location(latitude,longitude));

            val szerokosc = view.findViewById<TextView>(R.id.szerokosc)
            val dlugosc = view.findViewById<TextView>(R.id.dlugosc)
            szerokosc.text=longitude.toString();
            dlugosc.text=latitude.toString();
            var wsch = view.findViewById<TextView>(R.id.wsch);
            wsch.text=astro.moonInfo.moonrise.toString();
            var zach = view.findViewById<TextView>(R.id.zach);
            zach.text=astro.moonInfo.moonset.toString();
            var wchoazymut = view.findViewById<TextView>(R.id.wchoazymut);
            wchoazymut.text=astro.moonInfo.nextNewMoon.toString();
            var zachazymut = view.findViewById<TextView>(R.id.zachazymut);
            zachazymut.text=astro.moonInfo.nextFullMoon.toString();
            var zmierzch = view.findViewById<TextView>(R.id.zmierzch);
            zmierzch.text=astro.moonInfo.age.toString();
            var cywil = view.findViewById<TextView>(R.id.cywil);
            cywil.text=astro.moonInfo.illumination.toString();



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
                    val datatime=AstroDateTime(now.year,now.monthValue,now.dayOfMonth,now.hour,now.minute,now.second,1,true);
                    val astro= AstroCalculator(datatime,AstroCalculator.Location(latitude,longitude));

                    val szerokosc = view.findViewById<TextView>(R.id.szerokosc)
                    val dlugosc = view.findViewById<TextView>(R.id.dlugosc)
                    szerokosc.text=longitude.toString();
                    dlugosc.text=latitude.toString();
                    var wsch = view.findViewById<TextView>(R.id.wsch);
                    wsch.text=astro.moonInfo.moonrise.toString();
                    var zach = view.findViewById<TextView>(R.id.zach);
                    zach.text=astro.moonInfo.moonset.toString();
                    var wchoazymut = view.findViewById<TextView>(R.id.wchoazymut);
                    wchoazymut.text=astro.moonInfo.nextNewMoon.toString();
                    var zachazymut = view.findViewById<TextView>(R.id.zachazymut);
                    zachazymut.text=astro.moonInfo.nextFullMoon.toString();
                    var zmierzch = view.findViewById<TextView>(R.id.zmierzch);
                    zmierzch.text=astro.moonInfo.age.toString();
                    var cywil = view.findViewById<TextView>(R.id.cywil);
                    cywil.text=astro.moonInfo.illumination.toString();



                }


                refreshHandler.postDelayed(this, refresh.toLong() * 1000)
            }
        }
        refreshHandler.postDelayed(runnable, refresh.toLong() * 1000)
    }


}