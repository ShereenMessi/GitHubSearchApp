package com.example.android.githubsearchapp;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
    GitArrayAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchStr = binding.searchEditText.getText().toString();
                if (!searchStr.isEmpty()) {
                    binding.searchButton.setEnabled(false);
                    GitAsynTask task = new GitAsynTask();
                    task.execute("https://api.github.com/search/repositories?q=" + binding.searchEditText.getText().toString() + "&sort=forks&order=desc");

                }
            }
        });
    }

    private void UpdateUi(Response response) {
        binding.searchButton.setEnabled(true);
        if (adapter == null) {
            adapter = new GitArrayAdapter(this, 0, response.getItems());
            binding.listView.setAdapter(adapter);

        } else {
            adapter.setItems(response.getItems());
            adapter.notifyDataSetChanged();
        }
    }

    private class GitAsynTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder JSONData = new StringBuilder();
            HttpURLConnection httpURLConnection = null;
            InputStream inputStream = null;
            try {
                URL url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setReadTimeout(15000);
                httpURLConnection.connect();
                inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                int lineNumber = 1;
                while (line != null) {
                    lineNumber++;
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
        protected void onPostExecute(String result) {
            if (!result.isEmpty()) {
                GsonBuilder gb = new GsonBuilder();
                gb.serializeNulls();
                Gson gson = new Gson();
                Response response = gson.fromJson(result, Response.class);
                UpdateUi(response);
            }
        }
    }
}



