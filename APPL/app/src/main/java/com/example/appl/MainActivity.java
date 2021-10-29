package com.example.appl;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout layout;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.id_layout);

        TextView textViewCode = new TextView(this);
        textViewCode.setId(View.generateViewId());
        textViewCode.setText("Hello Again");
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT);
        textViewCode.setLayoutParams(params);
        layout.addView(textViewCode);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);
        constraintSet.connect(textViewCode.getId(),ConstraintSet.TOP,layout.getId(),ConstraintSet.TOP);


        constraintSet.setVerticalBias(textViewCode.getId(),25f);
        constraintSet.setHorizontalBias(textViewCode.getId(),50f);
        constraintSet.applyTo(layout);


    }
}