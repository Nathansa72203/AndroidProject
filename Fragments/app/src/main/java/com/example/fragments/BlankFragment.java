package com.example.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class BlankFragment extends Fragment {
    SendInformation send;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_blank, container, false);
        Button b = v.findViewById(R.id.id_button2);
        b.setText("Clickable");

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.setText("Fragment Button Clicked");
                send.changeText("HI");
            }
        });
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        send = (SendInformation) context;
    }

    public interface SendInformation{
        public void changeText(String str);
    }

}