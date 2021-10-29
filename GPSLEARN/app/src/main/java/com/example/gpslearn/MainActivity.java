package com.example.gpslearn;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {
    long minTime = 400;
    float minDistance = 1.0f;
    ArrayList<Location> locationList = new ArrayList<Location>();
    TextView longitudeText;
    TextView latitudeText;
    TextView addressText;
    TextView distanceText;
    TextView timeText;
    long time = 0;
    long StartTime = 0;
    LocationManager locationManager;
    float totalDistance;
    final int intTemp = 1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        longitudeText = findViewById(R.id.longitudeText);
        latitudeText = findViewById(R.id.latText);
        addressText = findViewById(R.id.adressText);
        timeText = findViewById(R.id.timeText);
        distanceText = findViewById(R.id.distanceText);
        SystemClock.setCurrentTimeMillis(0);
        StartTime = SystemClock.elapsedRealtime() / 1000;

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, intTemp);
        }
        else if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
    }

    @Override
    public void onLocationChanged(@NonNull Location homeLocation) {

        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            homeLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            latitudeText.setText("Latitude: " + homeLocation.getLatitude());
            longitudeText.setText("Longitude: " + homeLocation.getLongitude());
            String timePrint = "";
            time = SystemClock.elapsedRealtime() / 1000 - StartTime;
            long seconds = 0;
            long minutes = 0;
            long hours = 0;
            if(time>60) {
                seconds = time % 60;
                if(time>3600)
                {
                    minutes = time%3600;
                    hours = time/3600;
                    timeText.setText("Time: " + hours +" hours, "+minutes+" minutes, "+seconds+" seconds");
                }
                else {
                    minutes = time / 60;
                    timeText.setText("Time: " + minutes + " minutes, " + seconds + " seconds");
                }
            }
            else {
                seconds=time;
                timeText.setText("Time: " + seconds + " seconds");
            }
        } catch (SecurityException e) {
        }
        try {
            addresses = geocoder.getFromLocation(homeLocation.getLatitude(), homeLocation.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            addressText.setText("Address: " + address + " " + city + " " + state + " " + country + " " + postalCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (homeLocation != null) {
            locationList.add(homeLocation);
            if (locationList.size() > 2) {
                totalDistance += homeLocation.distanceTo(locationList.get(locationList.size() - 2));
                BigDecimal bd = new BigDecimal(Double.toString(totalDistance * 0.000621371));
                bd = bd.setScale(10, RoundingMode.HALF_UP);
                distanceText.setText("Distance: " + bd.doubleValue() + " miles");
            }
        }

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case intTemp:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                    Log.i("NS23", "Hi");
                    break;
                }
        }

    }

}