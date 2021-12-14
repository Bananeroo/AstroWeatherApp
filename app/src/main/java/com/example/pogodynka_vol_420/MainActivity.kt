package com.example.pogodynka_vol_420

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.survivingwithandroid.weatherapp.model.Weather
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    var longitude : Double= -1.0;
    var latitude : Double= -1.0;
    var refresh : Int= 1;
    var weather:Weather= Weather();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        longitude = intent.getDoubleExtra("longitude",-1.0)
        latitude = intent.getDoubleExtra("latitude",-1.0)
        refresh = intent.getIntExtra("refresh",1)
        if(longitude!=-1.0)
            weather = intent.getSerializableExtra("klasa") as Weather

        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        supportFragmentManager.beginTransaction().apply {
            val bundle = Bundle()
            bundle.putDouble("longitude", longitude)
            bundle.putDouble("latitude", latitude)
            bundle.putInt("refresh", refresh)
            bundle.putSerializable("klasa", weather)
            val fragInfo = FirstFragment();
            fragInfo.setArguments(bundle);
            replace(R.id.flFragment, fragInfo)
            commit()
        }
        val ksiezyc = findViewById<Button>(R.id.Ksiezyc);
        ksiezyc.setOnClickListener(){

            supportFragmentManager.beginTransaction().apply {
                val bundle = Bundle()
                 bundle.putDouble("longitude", longitude)
                bundle.putDouble("latitude", latitude)
                bundle.putInt("refresh", refresh)
                bundle.putSerializable("klasa", weather)
                val fragInfo = SecondFragment();
                fragInfo.setArguments(bundle);
                replace(R.id.flFragment, fragInfo)
                commit()

            }
        }
        val slonce = findViewById<Button>(R.id.Slonce);
        slonce.setOnClickListener(){
            supportFragmentManager.beginTransaction().apply {

                val bundle = Bundle()
                bundle.putDouble("longitude", longitude)
                bundle.putDouble("latitude", latitude)
                bundle.putInt("refresh", refresh)
                bundle.putSerializable("klasa", weather)
                val fragInfo = FirstFragment();
                fragInfo.setArguments(bundle);
                replace(R.id.flFragment, fragInfo)
                commit()
            }
        }
        val basic = findViewById<Button>(R.id.basic);
        basic.setOnClickListener(){
            supportFragmentManager.beginTransaction().apply {

                val bundle = Bundle()
                bundle.putDouble("longitude", longitude)
                bundle.putDouble("latitude", latitude)
                bundle.putInt("refresh", refresh)
                bundle.putSerializable("klasa", weather)
                val fragInfo = ThirdFragment();
                fragInfo.setArguments(bundle);
                replace(R.id.flFragment, fragInfo)
                commit()
            }
        }
        val bonus = findViewById<Button>(R.id.bonus);
        bonus.setOnClickListener(){
            supportFragmentManager.beginTransaction().apply {

                val bundle = Bundle()
                bundle.putDouble("longitude", longitude)
                bundle.putDouble("latitude", latitude)
                bundle.putInt("refresh", refresh)
                bundle.putSerializable("klasa", weather)
                val fragInfo = FourthFragment();
                fragInfo.setArguments(bundle);
                replace(R.id.flFragment, fragInfo)
                commit()
            }
        }
        val next = findViewById<Button>(R.id.next);
        next.setOnClickListener(){
            supportFragmentManager.beginTransaction().apply {

                val bundle = Bundle()
                bundle.putDouble("longitude", longitude)
                bundle.putDouble("latitude", latitude)
                bundle.putInt("refresh", refresh)
                bundle.putSerializable("klasa", weather)
                val fragInfo = FifthFragment();
                fragInfo.setArguments(bundle);
                replace(R.id.flFragment, fragInfo)
                commit()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {

                val intent = Intent(this@MainActivity, Settings::class.java)
                startActivity(intent)

                true
            }
            else -> super.onOptionsItemSelected(item)
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
        refresh=savedInstanceState.getInt("refresh",1)
        if(longitude!=-1.0)
            weather = savedInstanceState.getSerializable("klasa") as Weather


    }



}