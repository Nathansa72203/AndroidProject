package com.example.cookieclicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity{
    ImageView cookie;
    TextView counter;
    TextView oneAdd;
    ArrayList<ImageView> imageList = new ArrayList<ImageView>();
    ViewGroup constraintLayout;
    ArrayList<Integer> purchasedItemsRate = new ArrayList<Integer>();
    ArrayList<Integer> imageId = new ArrayList<Integer>();
    Button cookieFarmer, autoClicker;
    static AtomicInteger clicksCounter;
    int increment = 0;
    LinearLayout linearLayout,linearLayoutb;
    int clickCost = 10;
    int farmerCost = 30;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        constraintLayout = findViewById(R.id.layout_id);
        cookie = findViewById(R.id.id_cookie);
        autoClicker = findViewById(R.id.autoMouseB);
        cookieFarmer = findViewById(R.id.farmerB);
        cookie.setImageResource(R.drawable.cookieg);
        counter = findViewById(R.id.id_counter);
        linearLayout = findViewById(R.id.id_linearLayout);
        linearLayoutb = findViewById(R.id.linearLayoutB);
        clicksCounter = new AtomicInteger(0);
        counter.setText(clicksCounter+" Cookies");
        autoClicker.setClickable(false);
        cookieFarmer.setClickable(false);
        passiveIncome pI = new passiveIncome();
        pI.start();


        final ScaleAnimation cookieSizeChange = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        cookieSizeChange.setDuration(200);
        cookie.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                cookie.startAnimation(cookieSizeChange);
                clicksCounter.getAndIncrement();
                counter.setText(clicksCounter+" Cookies");
                oneAdd = new TextView(MainActivity.this);
                oneAdd.setText("+1");
                int xLoc = (int) (Math.random() * 400) + 50;
                oneAdd.setX(xLoc);
                constraintLayout.addView(oneAdd);
                final TranslateAnimation translateAnimation = new TranslateAnimation(0,0,400,100);
                translateAnimation.setDuration(1500);
                oneAdd.startAnimation(translateAnimation);
                oneAdd.setVisibility(View.INVISIBLE);

            }
        });
    }

        public class passiveIncome extends Thread
        {
            public void run() {
                while (true) {

                    autoClicker.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onClick(View v) {
                            purchasedItemsRate.add(1);
                            imageId.add(R.drawable.cur);
                            clicksCounter.getAndAdd(-clickCost);
                            ImageView imagea = new ImageView(getApplicationContext());
                            imagea.setImageDrawable(getDrawable(R.drawable.cur));
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 200);
                            imagea.setLayoutParams(layoutParams);
                            imageList.add(imagea);
                            linearLayout.addView(imagea);
                        }
                    });
                    cookieFarmer.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onClick(View v) {
                            purchasedItemsRate.add(5);
                            imageId.add(R.drawable.farmer);
                            clicksCounter.getAndAdd(-farmerCost);
                            ImageView imageb = new ImageView(getApplicationContext());
                            imageb.setImageDrawable(getDrawable(R.drawable.farmer));
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 200);
                            imageb.setLayoutParams(layoutParams);
                            imageList.add(imageb);
                            linearLayoutb.addView(imageb);
                        }
                    });
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (clicksCounter.get() >= clickCost) {
                                autoClicker.setClickable(true);
                            }
                            else{
                                autoClicker.setClickable(false);
                            }
                            if (clicksCounter.get() >= farmerCost)
                                cookieFarmer.setClickable(true);
                            else
                                cookieFarmer.setClickable(false);
                            increment = 0;
                            for (int i = 0; i < purchasedItemsRate.size(); i++) {
                                increment += purchasedItemsRate.get(i);
                            }
                            clicksCounter.getAndAdd(increment);
                            counter.setText(clicksCounter+" Cookies");
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }





    }



