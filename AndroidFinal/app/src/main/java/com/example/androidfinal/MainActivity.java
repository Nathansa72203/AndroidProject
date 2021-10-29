package com.example.androidfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.MapFragment;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static String country;
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    String[] arr;
    SharedPreferences preferences;
    public static final String MY_SHARED_PREFERENCES = "MySharedPrefs" ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.id_tabLayout);
        viewPager = findViewById(R.id.id_veiwPager);
        pagerAdapter = new pagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        preferences = getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    }

    public String getMyData() {
        Log.d("NASS","hIhihihih");
        return country;
    }

    public String sendData() {
        return country;
    }
}