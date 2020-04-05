package com.hasib.newsreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    String topStoriesAPI = "https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty";

    public static boolean checkingConnectivity = false;
    private List<String> newsID = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;

    public static SQLiteDatabase articlesDB;

    private void updateRecyclerView() {
        Cursor c = articlesDB.rawQuery("SELECT * FROM articles", null);

        int idIndex = c.getColumnIndex("id");

        if (c.moveToFirst()) {
            newsID.clear();

            do {
                newsID.add(c.getString(idIndex));
                Log.d(TAG, "updateRecyclerView: "+c.getString(idIndex));
            } while (c.moveToNext());

            adapter.notifyDataSetChanged();
        }

//        initRecyclerView();
    }

//    private void initRecyclerView() {
//        Log.d(TAG, "initRecyclerView: init recyclerview");
//
//        RecyclerView recyclerView = findViewById(R.id.recyclerView);
//        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, newsID, content);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//    }

    private void checkConnection() {
        ConnectivityManager manager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                checkingConnectivity = true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                checkingConnectivity = true;
            } else {
                checkingConnectivity = false;
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkConnection();

        articlesDB = this.openOrCreateDatabase("Articles", MODE_PRIVATE, null);
        articlesDB.execSQL("CREATE TABLE IF NOT EXISTS articles (id INTEGER PRIMARY KEY, articleId INTEGER, title VARCHAR, url VARCHAR, content VARCHAR)");

        DownloadTask downloadTask = new DownloadTask();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter(this, newsID);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (checkingConnectivity) {
            try {
                downloadTask.execute(topStoriesAPI);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection;

            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);

                Log.d(TAG, "doInBackground: Stream connected");

                int n;
                String newsIds = "";
                while ((n = reader.read()) != -1) {
                    newsIds += (char) n;
                }

                JSONArray jsonArray = new JSONArray(newsIds);
                int numberOfNews;
                 if (jsonArray.length() < 20) {
                     numberOfNews = jsonArray.length();
                 } else {
                     numberOfNews = 20;
                 }

                 for (int i=0; i<numberOfNews; i++) {
                     String news = "";

                     url = new URL("https://hacker-news.firebaseio.com/v0/item/"+jsonArray.getString(i)+".json?print=pretty");
                     urlConnection = (HttpURLConnection) url.openConnection();
                     urlConnection.connect();

                     inputStream = urlConnection.getInputStream();
                     reader = new InputStreamReader(inputStream);

                     while((n = reader.read()) != -1) {
                         news += (char) n;
                     }
                     //Log.d(TAG, "doInBackground: news " + news);

                     JSONObject newsObject = new JSONObject(news);
                     if (!newsObject.isNull("title") && !newsObject.isNull("url") && !newsObject.isNull("id")) {
                         String dbnewsTitle = newsObject.getString("title");
                         String dbnewsURL = newsObject.getString("url");
                         String dbnewsID = newsObject.getString("id");

                         Log.d(TAG, "doInBackground: news++" + dbnewsID +" "+ dbnewsTitle + " "+ dbnewsURL);

                         url = new URL(dbnewsURL);
                         urlConnection = (HttpURLConnection) url.openConnection();
                         urlConnection.connect();

                         BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());

                         String newsContent="";
                         while ((n = bufferedInputStream.read()) != -1) {
                             newsContent += (char) n;
                         }
                         Log.d(TAG, "doInBackground: newsContent " + newsContent);

                         String sql = "INSERT INTO articles (articleId, title, url, content) VALUES (?, ?, ?, ?)";

                         SQLiteStatement statement = articlesDB.compileStatement(sql);
                         statement.bindString(1, dbnewsID);
                         statement.bindString(2, dbnewsTitle);
                         statement.bindString(3, dbnewsURL);
                         statement.bindString(4, newsContent);

                         statement.execute();
                     }
                 }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            updateRecyclerView();
        }
    }
}
