package com.example.pogodynka_vol_420

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import com.survivingwithandroid.weatherapp.model.Location
import com.survivingwithandroid.weatherapp.model.Weather
import org.jetbrains.anko.doAsync
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.URL


internal class InternetCheck(private val mConsumer: Consumer) : AsyncTask<Void, Void, Boolean>() {
    interface Consumer {
        fun accept(internet: Boolean?)
    }

    init {
        execute()
    }

    override fun doInBackground(vararg voids: Void): Boolean? {
        try {
            val sock = Socket()
            sock.connect(InetSocketAddress("8.8.8.8", 53), 1500)
            sock.close()
            return true
        } catch (e: IOException) {
            return false
        }

    }

    override fun onPostExecute(internet: Boolean?) {
        mConsumer.accept(internet)
    }
}

class Settings : AppCompatActivity() {
    var longitude: Double=-1.0;
    var latitude: Double=-1.0;
    var refresh: Int=1;
    var weather:Weather= Weather();
    var intconnect:Boolean=false;
    @Throws(JSONException::class)
    private fun getObject(tagName: String, jObj: JSONObject): JSONObject {
        return jObj.getJSONObject(tagName)
    }

    @Throws(JSONException::class)
    private fun getString(tagName: String, jObj: JSONObject): String? {
        return jObj.getString(tagName)
    }

    @Throws(JSONException::class)
    private fun getFloat(tagName: String, jObj: JSONObject): Float {
        return jObj.getDouble(tagName).toFloat()
    }

    @Throws(JSONException::class)
    private fun getInt(tagName: String, jObj: JSONObject): Int {
        return jObj.getInt(tagName)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        var arraySpinner = arrayOf(
        "Brak do wyboru"
        )
    try {
        openFileInput("miasta.txt").use { stream ->
            val text = stream?.bufferedReader().use {
                it?.readText()
            }
            if (text != null) {

                arraySpinner = (text.lines().distinct()).toTypedArray()
            }
        }
    }catch (e:Exception){}

        val s = findViewById<Spinner>(R.id.spiner) as Spinner
        s.setSelection(0);
        s.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                if (parentView != null) {
                    val text= parentView.getItemAtPosition(position).toString()
                    val x = findViewById<TextView>(R.id.miasto);
                    x.text=text
                }
            }
            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        })

        var arraySpinner1 = arrayOf(
                "C","F","K"
        )
        val sa = findViewById<Spinner>(R.id.spiner1) as Spinner
        sa.setSelection(0);
        sa.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View,
                    position: Int,
                    id: Long
            ) {
                if (parentView != null) {
                    weather.stopnie=position;
                }
            }
            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        })


        val adapter = ArrayAdapter(
                this,
                R.layout.spinner_item, arraySpinner
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        s.adapter = adapter

        val adapter1 = ArrayAdapter(
                this,
                R.layout.spinner_item, arraySpinner1
        )
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sa.adapter = adapter1



        InternetCheck(object : InternetCheck.Consumer {
            override fun accept(internet: Boolean?) {
                if (internet != null) {
                    intconnect= internet
                }
            }
        })
        val button1 = findViewById<Button>(R.id.zapisz);
        button1.setOnClickListener(){
            val miasto = findViewById<EditText>(R.id.miasto).text.toString();
            val a = "https://api.openweathermap.org/data/2.5/weather?q="
            val c = "&units=metric&appid=feafaa605a6c1baa4af967d6d2a1c5ee"

            val e ="http://api.openweathermap.org/data/2.5/forecast?q="
            val f ="&appid=feafaa605a6c1baa4af967d6d2a1c5ee"
            val g = "$e$miasto$f"
            doAsync {

                val loc = Location()
                if(intconnect){


                    val d="$a$miasto$c"
                    val xD = URL(d).readText()
                    // We create out JSONObject from the data

                    // We create out JSONObject from the data
                    val jObj = JSONObject(xD)

                    // We start extracting the info

                    // We start extracting the info

                    val coordObj: JSONObject = getObject("coord", jObj)
                    loc.setLatitude(getFloat("lat", coordObj))
                    loc.setLongitude(getFloat("lon", coordObj))

                    val sysObj: JSONObject = getObject("sys", jObj)
                    loc.setCountry(getString("country", sysObj))
                    loc.setSunrise((getInt("sunrise", sysObj)).toLong())
                    loc.setSunset((getInt("sunset", sysObj)).toLong())
                    loc.setCity(getString("name", jObj))
                    weather.location = loc

                    // We get weather info (This is an array)

                    // We get weather info (This is an array)
                    val jArr: JSONArray = jObj.getJSONArray("weather")

                    // We use only the first value
                    // We use only the first value
                    val JSONWeather = jArr.getJSONObject(0)
                    weather.currentCondition.weatherId = getInt("id", JSONWeather)
                    weather.currentCondition.descr = getString("description", JSONWeather)
                    weather.currentCondition.condition = getString("main", JSONWeather)
                    weather.currentCondition.icon = getString("icon", JSONWeather)

                    val mainObj: JSONObject = getObject("main", jObj)
                    weather.currentCondition.humidity = (getInt("humidity", mainObj)).toFloat()
                    weather.currentCondition.pressure = (getInt("pressure", mainObj)).toFloat()
                    weather.temperature.maxTemp = getFloat("temp_max", mainObj)
                    weather.temperature.minTemp = getFloat("temp_min", mainObj)
                    weather.temperature.temp = getFloat("temp", mainObj)

                    // Wind

                    // Wind
                    val wObj: JSONObject = getObject("wind", jObj)
                    weather.wind.speed = getFloat("speed", wObj)
                    weather.wind.deg = getFloat("deg", wObj)

                    // Clouds
                    try {
                        val zObj: JSONObject = getObject("rain", jObj)
                        weather.rain.ammount = getFloat("1h", zObj)
                    }catch (e: Exception){
                        weather.rain.ammount="0".toFloat()
                    }

                    // Clouds
                    val cObj: JSONObject = getObject("clouds", jObj)
                    weather.clouds.perc = getInt("all", cObj)




                    latitude=weather.location.latitude.toDouble();
                    longitude=weather.location.longitude.toDouble();
                    weather.intconnect=true;
                    openFileOutput("test.txt", Context.MODE_PRIVATE).use { output ->
                        if (output != null) {

                            output.write((weather.location.city+"\n").toByteArray())
                            output.write((weather.location.latitude.toString()+"\n").toByteArray())
                            output.write((weather.location.longitude.toString()+"\n").toByteArray())
                            output.write((weather.temperature.temp.toString()+"\n").toByteArray())
                            output.write((weather.currentCondition.pressure.toString()+"\n").toByteArray())
                            output.write((weather.currentCondition.descr.toString()+"\n").toByteArray())
                            output.write((weather.wind.speed.toString()+"\n").toByteArray())
                            output.write((weather.wind.deg.toString()+"\n").toByteArray())
                            output.write((weather.currentCondition.humidity.toString()+"\n").toByteArray())
                            output.write((weather.rain.ammount.toString()+"\n").toByteArray())
                            output.write((weather.currentCondition.icon.toString()+"\n").toByteArray())
                            output.write((weather.stopnie.toString()+"\n").toByteArray())
                        }
                    }
                    var text:String?=""
                    try {
                        openFileInput("miasta.txt").use { stream ->
                            text = stream?.bufferedReader().use {
                                it?.readText()
                            }
                        }
                    }catch (e:java.lang.Exception){}
                    openFileOutput("miasta.txt", Context.MODE_PRIVATE).use { output ->
                        if (output != null) {
                            output.write(text?.toByteArray())
                            output.write((weather.location.city+"\n").toByteArray())
                        }
                    }

                }
                else{
                    openFileInput("test.txt").use { stream ->
                        val text = stream?.bufferedReader().use {
                            it?.readText()
                        }
                         if (text != null) {
                            val linie =text.lines()

                             weather.location = loc
                             weather.location.city=(linie.get(0)+" (Not up to date)")
                             weather.location.latitude=(linie.get(1)).toFloat();
                             weather.location.longitude=(linie.get(2)).toFloat();
                             weather.temperature.temp=(linie.get(3)).toFloat();
                             weather.currentCondition.pressure=(linie.get(4)).toFloat();
                             weather.currentCondition.descr=(linie.get(5)).toString();
                             weather.wind.speed=(linie.get(6)).toFloat();
                             weather.wind.deg=(linie.get(7)).toFloat();
                             weather.currentCondition.humidity=(linie.get(8)).toFloat();
                             weather.rain.ammount=(linie.get(9)).toFloat();
                             weather.currentCondition.icon=(linie.get(10)).toString();
                             weather.stopnie=(linie.get(11)).toInt();

                             latitude=weather.location.latitude.toDouble();
                             longitude=weather.location.longitude.toDouble();
                             weather.intconnect=false;
                         }
                    }


                }


                val intent = Intent(this@Settings, MainActivity::class.java)
                intent.putExtra("longitude", longitude);
                intent.putExtra("latitude", latitude);
                intent.putExtra("refresh", refresh);
                intent.putExtra("klasa", weather);

                startActivity(intent)
            }


        }





    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble("latitude",latitude)
        outState.putDouble("longitude",longitude)
        outState.putInt("refresh",refresh)
        outState.putSerializable("klasa",weather)

    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        longitude=savedInstanceState.getDouble("longitude",-1.0)
        latitude=savedInstanceState.getDouble("latitude",-1.0)
        refresh=savedInstanceState.getInt("refresh",-1)
        if(longitude!=-1.0)
            weather= savedInstanceState.getSerializable("klasa") as Weather

    }

}