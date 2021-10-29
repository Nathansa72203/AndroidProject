package com.example.androidfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragOne#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragOne extends Fragment{
    //SendInformation send;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    String country = "France";
    private Object fragTwo;
    Button button;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    public fragOne(){
        // Required empty public constructor
    }
    public static fragOne newInstance(String param1, String param2) {
        fragOne fragment = new fragOne();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    View googleMapa;
    double latitude;
    double longitude;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_one, container, false);
        button = v.findViewById(R.id.selected_id);
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);
        sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        Log.d("NNSS","HIT");
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title(latLng.latitude+" : "+latLng.longitude);
                        googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                        googleMap.addMarker(markerOptions);
                        Log.d("NS22", String.valueOf(latLng));
                        latitude = latLng.latitude;
                        longitude = latLng.longitude;

                    }
                });
            }
        });
        // Fragment frag= getActivity().getSupportFragmentManager().findFragmentByTag("");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation(getContext(),latitude,longitude);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("country_name", country);
                editor.commit();
                Log.d("NASS1111",country);
                //  ((fragTwo)frag).passIt(country);
            }
        });
        return v;
    }
    public String getLocation(Context context, double lat, double lon)
    {
        String fin ="";
        try{
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addressList = geocoder.getFromLocation(lat,lon,1);
            if(addressList.size()>0)
            {
                Address address = addressList.get(0);
                fin = address.getAddressLine(0);
                Log.d("NS22",fin);
                country = address.getCountryName();
                Log.d("NS222222","Country: "+country);

            }
        }catch(IOException IOE){}
        return fin;
    }


}