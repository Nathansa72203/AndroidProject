package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.id_recycler);

        ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i< 10; i++)
        {
            list.add(Integer.toString(i));
        }
        RecyclerAdaptor recyclerAdaptor = new RecyclerAdaptor(this,list);
        recyclerView.setAdapter(recyclerAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

    }
}