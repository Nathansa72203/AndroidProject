package com.example.jsonarraytest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JSONObject albumCollection = new JSONObject();

        JSONObject albumOne = new JSONObject();

        try {
            albumOne.put("Artist Name", "Eminem");
            albumOne.put("Year", "2018");
            albumOne.put("Name Of Album", "Kamikaze");
            JSONArray albumOneSongList = new JSONArray();
            albumOneSongList.put("The Ringer");
            albumOneSongList.put("Lucky You");
            albumOneSongList.put("Paul" );
            albumOneSongList.put("Normal");
            albumOneSongList.put("Stepping Stone");
            albumOneSongList.put("Stepping Stone");
            albumOneSongList.put("Kamikaze");
            albumOneSongList.put("Fall");
            albumOneSongList.put("Nice Guy" );
            albumOneSongList.put("Good Guy");
            albumOneSongList.put("Venom");
            albumOne.put("Song List",albumOneSongList);
            albumCollection.put("Kamikaze",albumOne);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("TAG_1", albumOne.toString());



    }
}


