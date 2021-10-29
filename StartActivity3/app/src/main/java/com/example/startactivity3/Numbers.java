package com.example.startactivity3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Numbers extends AppCompatActivity {
    Button button;
    TextView textView;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
        textView = findViewById(R.id.id_numbers_text);
        button = findViewById(R.id.id_numbers_finishButton);
        editText = findViewById(R.id.id_numbers_plainText);

        textView.setText(getIntent().getStringExtra("TEST"));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendInfoBack = new Intent();
                sendInfoBack.putExtra(MainActivity.RETURN_KEY,editText.getText().toString());
                setResult(RESULT_OK,sendInfoBack);
                finish();
            }
        });
    }
}