package com.example.jsondemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JSONObject rootObject = new JSONObject();

        try {
            rootObject.put("name", "Nathan Sankar");
            rootObject.put("id", "10014294");
            rootObject.put("age", "20");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("TAG_1", rootObject.toString());

        JSONObject computerSceinceClass = new JSONObject();

        try {
            computerSceinceClass.put("block", "4A");
            computerSceinceClass.put("class name", "Android");
            computerSceinceClass.put("grade", "95.5");
            //add comp sci json object the the root json
            rootObject.put("computer sceince",computerSceinceClass);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject extra = new JSONObject();
        String a = null;
        try {
            a = computerSceinceClass.getJSONObject("computer sceince").get("grade").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("TAG_3",a);

    }
}