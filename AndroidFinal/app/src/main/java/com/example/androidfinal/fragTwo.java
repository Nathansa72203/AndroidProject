package com.example.androidfinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.util.NumberUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class fragTwo extends Fragment implements ShareIt {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    String country;
    URL url;
    Button button;
    TextView totalCases;
    TextView totalDeaths;
    TextView newCases;
    TextView newDeaths;
    TextView countrytextView;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    public fragTwo() {
    }
    public static fragTwo newInstance(String param1, String param2) {
        fragTwo fragment = new fragTwo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_two, container, false);
        button = v.findViewById(R.id.button);
        totalCases = v.findViewById(R.id.totalCases_id);
        totalDeaths = v.findViewById(R.id.totalDeaths_id);
        newCases = v.findViewById(R.id.newCases_id);
        newDeaths = v.findViewById(R.id.newDeaths_id);
        countrytextView =  v.findViewById(R.id.country_name);
        // Bundle bundle = this.getArguments();
        //country = bundle.getString("country_name");


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        country = sharedPreferences.getString("country_name",null);
        Log.d("NAEND",country);

        //country = "Italy";
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                country = sharedPreferences.getString("country_name",null);
                String urlString = "https://corona.lmao.ninja/v2/countries/"+country+"?yesterday&strict&query%20";
                MyAsyncTask asyncTask = new MyAsyncTask();
                asyncTask.execute(urlString);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.id_frame, new fragGraphs());
                fragmentTransaction.commit();
                countrytextView.setText(country);
            }
        });



        return v;
    }

    @Override
    public void passIt(String data) {
        country = data;
        Log.d("PLSSSS",country);
    }

    class MyAsyncTask extends AsyncTask<String, Void, String> implements com.example.androidfinal.MyAsyncTask {

        BufferedReader reader = null;
        URLConnection urlConnection = null;

        @Override
        protected String doInBackground(String... urlLines) {
            String line = urlLines[0];
            try {
                Log.d("TAGN1: ", " " + line);
                url = new URL(line);
                urlConnection = url.openConnection();
                InputStream stream = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                line = reader.readLine();
            } catch (MalformedURLException ex) {
            } catch (IOException e) {
                e.printStackTrace();
            }
            return line;
        }
        private boolean isNumeric(String a)
        {
            try {
                int val = Integer.parseInt(a);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            int tCStart = s.indexOf("\"cases\":")+8;
            int temp = 0;
            for(temp = 0; isNumeric(s.substring(tCStart+temp,tCStart+temp+1)); temp++)
            {}
            String totalCasesA = s.substring(tCStart,tCStart+temp);
            Log.d("NNSS",totalCasesA);

            int tDStart = s.indexOf("\"deaths\":")+9;
            for(temp = 0; isNumeric(s.substring(tDStart+temp,tDStart+temp+1)); temp++)
            {}
            String totalDeathsA = s.substring(tDStart,tDStart+temp);
            Log.d("NNSS",totalDeathsA);

            int nDStart = s.indexOf("\"todayDeaths\":")+14;
            for(temp = 0; isNumeric(s.substring(nDStart+temp,nDStart+temp+1)); temp++)
            {}
            String newDeathsA = s.substring(nDStart,nDStart+temp);
            Log.d("NNSS",newDeathsA);

            int nCStart = s.indexOf("\"todayCases\":")+13;
            for(temp = 0; isNumeric(s.substring(nCStart+temp,nCStart+temp+1)); temp++)
            {}
            String newCasesA = s.substring(nCStart,nCStart+temp);
            Log.d("NNSS",newCasesA);
            totalCases.setText(totalCasesA);
            totalDeaths.setText(totalDeathsA);
            newCases.setText(newCasesA);
            newDeaths.setText(newDeathsA);

        }
    }

}