package com.example.matrixproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.opengl.Matrix;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    Matrix matrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        matrix.transposeM();
    }
}