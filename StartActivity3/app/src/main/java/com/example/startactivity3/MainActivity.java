package com.example.startactivity3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button startButton;
    TextView displayText;
    public static final int INTENT_CODE = 1;
    public static final String RETURN_KEY = "KEY_1";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == INTENT_CODE && resultCode==RESULT_OK)
        {
            displayText.setText(data.getStringExtra(RETURN_KEY));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayText = findViewById(R.id.textView);
        startButton = findViewById(R.id.id_mian_button_start);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToLoad = new Intent(MainActivity.this,Numbers.class);
                intentToLoad.putExtra("TEST","This is a test");
                startActivityForResult(intentToLoad,INTENT_CODE);
            }
        });
    }
}