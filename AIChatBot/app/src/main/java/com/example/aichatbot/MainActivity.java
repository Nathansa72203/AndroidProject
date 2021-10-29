package com.example.aichatbot;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    SmsManager smsManager;
    String phoneNumber;
    BroadcastReceiver broadcastReceiver;
    String state = "Introduction";
    String[] stateList = {"Introduction","Asks Their Needs","Directs Shopers","Closure"};
    String[][] responseList =  new String[4][4];
    double totalPrice = 0;
    boolean firstTime = true;
    double tempCost = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_id);
        textView.setText("States:\nIntroduction -- 1\nTake Orders -- 2\nFinal Bill and Confirmation -- 3\nConclusion -- 4\nDone -- 5");
        smsManager = SmsManager.getDefault();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS,Manifest.permission.SEND_SMS,Manifest.permission.READ_PHONE_STATE},0);
        }

        broadcastReceiver = new BroadcastReceiver() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(MainActivity.this, "Text Received", Toast.LENGTH_SHORT).show();
                Bundle bundle = intent.getExtras();
                Object[] pdus = (Object[]) bundle.get("pdus");
                SmsMessage[] smsMessage = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    smsMessage[i] = SmsMessage.createFromPdu((byte[]) pdus[i], bundle.getString("format"));
                }
                    String message = smsMessage[0].getMessageBody();
                    phoneNumber = smsMessage[0].getOriginatingAddress();
                    phoneNumber=phoneNumber.substring(8,12);
                    textView.setText(state);
                    Handler handler = new Handler();
                    handler.postDelayed(findResponse(message.toLowerCase()),2000);
            }
        };

    }
    public boolean isNumber(String str)
    {
        if (str == null)
            return false;
        try {
            double num = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public String responseGetter(String text)
    {
        boolean askedQuantity = false;
        switch(state) {
            case "Introduction":
                state = "Take Orders";
                if (text.contains("hi") || text.contains("hello") || text.contains("hey"))
                    return "Hello. Welcome to Nathan's. What would you like to order today?";
                else if (text.contains("is this nathan's") || text.contains("is this nathans"))
                    return "Hello. Yes, this is Nathan's. What would you like to order?";
                else {
                    state = "Done";
                    return "Hello? I think you have reached the wrong number?";
                }
            case "Take Orders":
                if(isNumber(text)) {
                    totalPrice += (tempCost) * (Double.parseDouble(text)-1);
                    askedQuantity = false;
                    return "next?";
                }
                else if (text.contains("hot dog")){
                    tempCost = 3.29;
                    totalPrice += 3.29;
                    askedQuantity = true;
                    return "Quantity?";
                }
                else if(text.contains("cheese dog")) {
                    tempCost = 3.99;
                    totalPrice += 3.99;
                    askedQuantity = true;
                    return "Quantity?";
                }
                else if(text.contains("chili dog")) {
                    tempCost = 3.99;
                    totalPrice += 3.99;
                    askedQuantity = true;
                    return "Quantity?";
                }
                else if(text.contains("chili cheese dog")) {
                    tempCost = 4.99;
                    totalPrice += 4.99;
                    askedQuantity = true;
                    return "Quantity?";
                }
                if(text.contains("done")||text.contains("finished")||text.contains("thats all")||text.contains("done")){
                    state = "Final Bill and Confirmation";
                    Log.d("NA1", Double.toString(totalPrice));
                    totalPrice = totalPrice*100;
                    Log.d("NA1", Double.toString(totalPrice));
                    totalPrice = (int)totalPrice;
                    totalPrice/=100;
                    Log.d("NA1", Double.toString(totalPrice));

                    return "Ok. Thank you. Your bill is $"+totalPrice+". How do you want to pay?";
                }
                else
                    return "Please choose one of the options.";
            case "Final Bill and Confirmation":
                state = "Conclusion";
                if(text.contains("credit")||text.contains("debit")||text.contains("apple pay")||text.contains("gift card")||text.contains("visa")){
                    return "Ok. Please pay on our online application.";
                }
                else if(text.contains("cash")){
                    return "Ok. Please pay when the driver gets to your house.";
                }
                else {
                    state = "Final Bill and Confirmation";
                    return "I am sorry, we dont accept that form of payment. We take credit, debit, apple pay, gift cards, visa on our application.";
                }
            case "Conclusion":
                state = "Done";
                if(text.contains("done")||text.contains("ok")){
                    return "Thank you so much for coming to Nathan's. I hope you enjoy our food.";
                }
                else if(text.contains("bye")){
                    return "Goodbye. I hope you enjoy our food.";
                }
                else{
                    return "Goodbye.";
                }
            default:
                break;
        }
        return "";
    }
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(broadcastReceiver, intentFilter);
    }

    public Runnable findResponse(String text){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, state, Toast.LENGTH_SHORT).show();
                String response = responseGetter(text);
                Log.d("NA1", response);
                textView.setText(state);
                String menuText = "Menu: \n" + "Hot Dog: $3.29.\n" + "Cheese Dog: $3.99.\n" + "Chili Dog: $3.99.\n" + "Chili Cheese Dog: $4.69." + "\nPlease enter each food in its own individual text";
                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                if(!state.equals("end"))
                    smsManager.sendTextMessage(phoneNumber, null, response, null, null);
                if(state.equals("Done"))
                    state = "end";
                if(firstTime && !state.equals("end")) {
                    smsManager.sendTextMessage(phoneNumber, null, menuText, null, null);
                    firstTime = false;
                }
            }
        };
        return runnable;
    }
}