package com.example.spursh.youtube;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;


public class MainActivity extends Activity implements LocationListener,SensorEventListener, View.OnClickListener {
    private SensorManager mSensorManager;
    private Sensor mPressure;
    private Sensor mTemperature;
    private Sensor mHumidity;
    TextView textView1, textView2, textView3;

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat,txtLang;
    String lat;
    String provider;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
//we have a result
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    private Button scanBtn;
    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanBtn = (Button)findViewById(R.id.scan_button);
        scanBtn.setOnClickListener(this);

//        txtLat = (TextView) findViewById(R.id.latlang);
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
//        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
//            mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
//            mTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
//            mHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        }
    @Override
    public void onLocationChanged(Location location) {
        //txtLat = (TextView) findViewById(R.id.lat);
        //txtLang = (TextView) findViewById(R.id.lang);

        txtLat.setText("Latitude: " + location.getLatitude());
        txtLang.setText("Longitude: "+ location.getLongitude() );

        ////
        new CatFactsActivity().execute("http://192.168.1.4:8083/api/mongoInsert");
        ////

    }
    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

        @Override
        public final void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Do something here if sensor accuracy changes.
        }

        @Override
        public final void onSensorChanged(SensorEvent event) {
            Sensor sensor =event.sensor;
            if(sensor.getType() == Sensor.TYPE_PRESSURE)
            {
                float pressure = event.values[0];
                 //textView1 = (TextView) findViewById(R.id.pressure);
                //textView1.setText(Float.toString(pressure));
            }
            else if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE)
            {
                float temperature = event.values[0];
                 //textView2 = (TextView) findViewById(R.id.temperature);
                //textView2.setText(Float.toString(temperature));
            }
            else if (sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY)
            {
                float humidity = event.values[0];
                 //textView3 = (TextView) findViewById(R.id.humidity);
                //textView3.setText(Float.toString(humidity));
            }
        }

        @Override
        protected void onResume() {
            // Register a listener for the sensor.
            super.onResume();
//            mSensorManager.registerListener(this, mPressure, SensorManager.SENSOR_DELAY_NORMAL);
//            mSensorManager.registerListener(this, mTemperature, SensorManager.SENSOR_DELAY_NORMAL);
//            mSensorManager.registerListener(this, mHumidity, SensorManager.SENSOR_DELAY_NORMAL);

        }
        @Override
        protected void onPause() {
            // Be sure to unregister the sensor when the activity pauses.
            super.onPause();
//            mSensorManager.unregisterListener(this);
        }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.scan_button) {
//scan

            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }
}