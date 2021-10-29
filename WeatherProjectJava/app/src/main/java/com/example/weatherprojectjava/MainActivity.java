package com.example.weatherprojectjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ImageView imageViewA;
    TextView tempTextA;
    TextView locTextA;
    TextView timeTextA;
    TextView weatherTextA;
    ImageView imageViewB;
    TextView tempTextB;
    TextView locTextB;
    TextView timeTextB;
    TextView weatherTextB;
    ImageView imageViewC;
    TextView tempTextC;
    TextView locTextC;
    TextView timeTextC;
    TextView weatherTextC;
    URL url;
    String line;
    Button button;
    String longitude="0";
    String latitude="0";
    EditText editTextLong;
    EditText editTextLat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageViewA = findViewById(R.id.imageViewA);
        locTextA = findViewById(R.id.locA);
        tempTextA = findViewById(R.id.tempA);
        timeTextA = findViewById(R.id.timeA);
        weatherTextA = findViewById(R.id.desA);
        imageViewB = findViewById(R.id.imageViewB);
        locTextB = findViewById(R.id.locB);
        tempTextB = findViewById(R.id.tempB);
        timeTextB = findViewById(R.id.timeB);
        weatherTextB = findViewById(R.id.desB);
        imageViewC = findViewById(R.id.imageViewC);
        locTextC = findViewById(R.id.locC);
        tempTextC = findViewById(R.id.tempC);
        timeTextC = findViewById(R.id.timeC);
        weatherTextC = findViewById(R.id.desC);

        button = findViewById(R.id.button);
        editTextLong = findViewById(R.id.editTextTwo);
        editTextLat = findViewById(R.id.editTextone);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               longitude = editTextLong.getText().toString();
               latitude = editTextLat.getText().toString();
                if(!latitude.equals("")&&!longitude.equals("")) {
                    String urlString = "https://api.openweathermap.org/data/2.5/find?lat=" + latitude + "&lon=" + longitude + "&cnt=3&appid=6260348e83dac78acd7cca26577f5745";
                    MyAsyncTask asyncTask = new MyAsyncTask();
                    asyncTask.execute(urlString);
                }
            }
        });
    }

    private class MyAsyncTask extends AsyncTask<String, Void, String> implements com.example.weatherprojectjava.MyAsyncTask {

        BufferedReader reader = null;
        URLConnection urlConnection = null;
        @Override
        protected String doInBackground(String... urlLines) {
            String line = urlLines[0];
            try {
                Log.d("TAGN1: ", " " + line);
                url = new URL(line);
                urlConnection = url.openConnection();
                InputStream stream = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                line = reader.readLine();
            }
            catch (MalformedURLException ex) {
            } catch (IOException e) {
                e.printStackTrace();
            }
            return line;
        }

        @Override
        protected void onPostExecute(String Jline) {
            int searchStartIndex = 0;
            double[] tempArray = new double[3];
            String[] locArray = new String[3];
            String[] timeArray = new String[3];
            String[] desArray = new String[3];
            int[] weatherArray = new int[3];
            boolean end = true;

//          Finding the temperature
            for(int index = 0; index<3;index++) {
                int tempStartIndex = Jline.indexOf("\"temp\"",searchStartIndex) + 7;
                int tempEndIndex = 0;
                end = true;
                for (int i = tempStartIndex; end && i < Jline.length(); i++) {
                    if (Jline.charAt(i) == ',') {
                        end = false;
                        tempEndIndex = i;
                        searchStartIndex = tempEndIndex;
                    }
                }
                double temperature = Double.parseDouble(Jline.substring(tempStartIndex, tempEndIndex));
                temperature = temperature * 9 / 5 - 459.67;
                temperature = (int) (temperature * 100);
                temperature /= 100;
                tempArray[index]=temperature;
            }

//          Finding the location
            searchStartIndex = 0;
            for(int index = 0; index<3;index++) {
                int locationStartIndex = Jline.indexOf("name",searchStartIndex) + 7;
                int locationEndIndex = 0;
                end = true;
                for (int i = locationStartIndex; end && i < Jline.length(); i++) {
                    if (Jline.charAt(i) == ',') {
                        end = false;
                        locationEndIndex = i - 1;
                        searchStartIndex = locationEndIndex;
                    }
                }
                locArray[index]=Jline.substring(locationStartIndex, locationEndIndex);
            }

//          Finding the time
            searchStartIndex = 0;
            for(int index = 0; index<3;index++) {
                int timeStartIndex = Jline.indexOf("dt",searchStartIndex)+4;
                int timeEndIndex = 0;
                end = true;
                for (int i = timeStartIndex; end && i < Jline.length(); i++) {
                    if (Jline.charAt(i) == ',') {
                        end = false;
                        timeEndIndex = i;
                        searchStartIndex = timeEndIndex;
                    }
                }
                int time = Integer.valueOf(Jline.substring(timeStartIndex, timeEndIndex));
                String epochString = Jline.substring(timeStartIndex, timeEndIndex);
                long e = Long.parseLong(epochString);
                Date timeR = new Date(e * 1000);
                String timeRet = timeR.toString();
                DateFormat dateFormat = new SimpleDateFormat("hh.mm aa ");
                DateFormat dateFormatB = new SimpleDateFormat("MM/dd/yyyy ");
                DateFormat dateFormatC = new SimpleDateFormat("EEEE");
                String dateString = dateFormat.format(timeR)+dateFormatB.format(timeR)+dateFormatC.format(timeR);
                timeArray[index]=dateString;
            }

//          Finding the description of the weather
            searchStartIndex = 0;
            int count  = 0;
            for(int index = 0; index<8;index++) {
                int desStartIndex = Jline.indexOf("main",searchStartIndex) + 7;
                int desEndIndex = 0;
                end = true;
                for (int i = desStartIndex; end && i < Jline.length(); i++) {
                    if (Jline.charAt(i) == ',') {
                        end = false;
                        desEndIndex = i - 1;
                        searchStartIndex=desEndIndex;
                    }
                }
                String des = Jline.substring(desStartIndex, desEndIndex);
                if(index==1||index==3||index==5)
                {
                    desArray[count]=des;
                    count++;
                }
            }
//          Finding the weather condition and finding the right picture
            searchStartIndex = 0;
            for(int index = 0; index<3;index++) {
                int weather = Integer.parseInt(Jline.substring(Jline.indexOf("weather\":[{\"id",searchStartIndex) + 16, Jline.indexOf("weather\":[{\"id",searchStartIndex) + 19));
                searchStartIndex = Jline.indexOf("weather\":[{\"id") + 7;
                if (weather == 800) {
                    weatherArray[index] = R.drawable.sunnyimage;
                } else {
                    weather /= 100;
                    switch (weather) {
                        case 2:
                            weatherArray[index] = R.drawable.thunderstormimage;
                            break;
                        case 3:
                            weatherArray[index] = R.drawable.weatherrain;
                            break;
                        case 5:
                            weatherArray[index] = R.drawable.weatherrain;
                            break;
                        case 6:
                            weatherArray[index] = R.drawable.weathersnow;
                            break;
                        case 8:
                            weatherArray[index] = R.drawable.weathercloudy;
                            break;
                        default:
                            weatherArray[index] = R.drawable.sunnyimage;
                    }
                }
            }


//          Putting the right words and images in the correct locations
            imageViewA.setImageResource(weatherArray[0]);
            tempTextA.setText(Double.toString(tempArray[0])+"°F");
            locTextA.setText(locArray[0]);
            timeTextA.setText(timeArray[0]);
            weatherTextA.setText(desArray[0]);
            imageViewB.setImageResource(weatherArray[1]);
            tempTextB.setText(Double.toString(tempArray[1])+"°F");
            locTextB.setText(locArray[1]);
            timeTextB.setText(timeArray[1]);
            weatherTextB.setText(desArray[1]);
            imageViewC.setImageResource(weatherArray[2]);
            tempTextC.setText(Double.toString(tempArray[2])+"°F");
            locTextC.setText(locArray[2]);
            timeTextC.setText(timeArray[1]);
            weatherTextC.setText(desArray[2]);

        }
    }



}
