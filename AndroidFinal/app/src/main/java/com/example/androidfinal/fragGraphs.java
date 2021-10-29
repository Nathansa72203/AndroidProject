package com.example.androidfinal;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class fragGraphs extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    String country;
    URL url;
    public fragGraphs() {
    }
    public static fragGraphs newInstance(String param1, String param2) {
        fragGraphs fragment = new fragGraphs();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_graphs, container, false);
        country = "Spain";
        String urlString = "https://corona.lmao.ninja/v2/historical/"+country+"?lastdays=30";
        fragGraphs.MyAsyncTask asyncTask = new fragGraphs.MyAsyncTask();
        asyncTask.execute(urlString);

        return v;
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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        private boolean isNumeric(String a) {
            try {
                int val = Integer.parseInt(a);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }


        }
    }
}