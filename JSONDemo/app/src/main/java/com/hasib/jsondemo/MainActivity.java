package com.hasib.jsondemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private EditText cityName;
    private TextView weatherShowingText;
    private String api;

    public void findWeather(View view) {
        DownloadTask downloadTask = new DownloadTask();

        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(cityName.getWindowToken(), 0);

        try {
            String encodedCityName = URLEncoder.encode(cityName.getText().toString(), "UTF-8");

            api = "https://api.openweathermap.org/data/2.5/weather?q="+encodedCityName+"&appid=d99dd4dc1cfb79da396e3518073c3aec";
            downloadTask.execute(api);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "City Name is Not Correct!", Toast.LENGTH_LONG).show();
        }
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection;
            String outout="";

            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);

                int n;
                while ((n = reader.read()) != -1) {
                    outout += (char) n;
                }

                return outout;
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "City Name is Not Correct!", Toast.LENGTH_LONG).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String output) {
            super.onPostExecute(output);

            Log.i("all info", output);

            JSONObject jsonObject;
            JSONObject mainJsonObject, windJsonObject, cloudJsonObject;
            try {
                jsonObject = new JSONObject(output);
                mainJsonObject = jsonObject.getJSONObject("main");
//                windJsonObject = jsonObject.getJSONObject("wind");
//                cloudJsonObject = jsonObject.getJSONObject("clouds");

                String weatherInfo = jsonObject.getString("weather");
                Log.i("Weather Content", weatherInfo);

                String finalOutputWeather ="";
                JSONArray array = new JSONArray(weatherInfo);
                for (int i=0; i<array.length(); i++) {
                    JSONObject jsonPart = array.getJSONObject(i);

                    finalOutputWeather += "\tWeather:\n\t\t\tMain: " + jsonPart.getString("main") +
                                            "\n\t\t\tDescription: " + jsonPart.getString("description") + "\n";
                }

                finalOutputWeather += "\tMain:\n\t\t\tTemp: " + mainJsonObject.getString("temp") +
                                            "\n\t\t\tPressure: " + mainJsonObject.getString("pressure") +
                                            "\n\t\t\tHumidity: " + mainJsonObject.getString("humidity") +
                                            "\n\t\t\tTemp_min: " + mainJsonObject.getString("temp_min") +
                                            "\n\t\t\tTemp_max: " + mainJsonObject.getString("temp_max");
//                                            "\n\tVisibility: " + jsonObject.getInt("visibility") +
//                                            "\tWind:\n\t\t\tSpeed: " + windJsonObject.getString("speed") +
//                                            "\n\tDeg: " + windJsonObject.getString("deg") +
//                                            "\n\tCloud: " + cloudJsonObject.getString("all");

                weatherShowingText.setText("");
                weatherShowingText.setText(finalOutputWeather);
                Log.i("output", finalOutputWeather);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "City Name is Not Correct!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName = (EditText) findViewById(R.id.takingCityNamesText);
        weatherShowingText = (TextView) findViewById(R.id.weatherShowingTextView);
    }
}
