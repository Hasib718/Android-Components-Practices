package com.hasib.downloadingimage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private Button logButton;
    private ImageView downloadedImage;

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap=null;
            URL url;
            HttpURLConnection httpURLConnection=null;

            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

                return bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logButton = (Button) findViewById(R.id.logButton);
        downloadedImage = (ImageView) findViewById(R.id.downloadedImage);
    }

    public void actionButton(View view) {
        DownloadImageTask downloadImageTask = new DownloadImageTask();
        Bitmap bitmapImage = null;

        try {
            bitmapImage = downloadImageTask.execute("https://upload.wikimedia.org/wikipedia/en/c/c1/SUST_1_km_entrance_way.jpg").get();
            downloadedImage.setImageBitmap(bitmapImage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Log.i("Result", "Image Printed");
    }
}
