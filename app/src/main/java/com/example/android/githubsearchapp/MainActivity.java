package com.example.android.githubsearchapp;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.githubsearchapp.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GitAsynTask task = new GitAsynTask();
        task.execute();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


    }

    private void UpdateUi(Response response) {
        GitArrayAdapter adapter = new GitArrayAdapter(this, 0, response.getItems());
        binding.listView.setAdapter(adapter);
    }

    private class GitAsynTask extends AsyncTask {

        @Override
        protected String doInBackground(Object[] objects) {
            StringBuilder JSONData = new StringBuilder();
            HttpURLConnection httpURLConnection = null;
            InputStream inputStream = null;
            try {
                URL url = new URL("https://api.github.com/search/repositories?q=android&sort=forks&order=desc");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setReadTimeout(15000);
                httpURLConnection.connect();
                inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                while (line != null) {
                    JSONData.append(line);
                    line = bufferedReader.readLine();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


            return JSONData.toString();
        }

        @Override
        protected void onPostExecute(Object o) {
            String JSONData = o.toString();
            if (!JSONData.isEmpty()) {
                GsonBuilder gb = new GsonBuilder();
                gb.serializeNulls();
                Gson gson = new Gson();
                Response response = gson.fromJson(JSONData, Response.class);
                UpdateUi(response);
            }
        }
    }
}



