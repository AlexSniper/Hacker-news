package com.example.newsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> titles = new ArrayList<String>();
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownLoadTask task = new DownLoadTask();// Starting new download task.
        try{
            task.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ListView listView = findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, titles); // Setting up ArrayAdapter
        listView.setAdapter(arrayAdapter);

    }
    public  class DownLoadTask extends AsyncTask<String, String , String> {

        @Override
        protected String doInBackground(String... urls) {

            String result ="";
            URL url ;
            HttpURLConnection urlConnection = null;
            try{
               url = new URL(urls[0]);
               urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int data =inputStreamReader.read();
                while (data != -1){
                    char current = (char)data;
                    result +=current;
                    data = inputStreamReader.read();// it will take to the next Item
                }
                JSONArray jsonArray = new JSONArray(result);
        int numberOfItems =20;
        if (jsonArray.length() < 20){
            numberOfItems = jsonArray.length();

        }
        for (int i=0; i<numberOfItems; i++) {
            String articleId = jsonArray.getString(i);
        }

                Log.i("URL Content", result);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}
