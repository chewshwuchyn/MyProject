package com.example.chewshwu.myproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyProject extends AppCompatActivity {

    String json_string;
    private TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_project);

        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
        tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        tvWelcome.setText(result);
    }

    public void getJSON(View view) {
        new BackgroundTask().execute();
    }

    public void taskList(View view){
      //  new BackgroundTask2().execute();
        startActivity(new Intent(this, TaskMain.class));

    }

    public void toDoList(View view){
        //  new BackgroundTask2().execute();
        new BackgroundTask2().execute();
    }
    /**
     * public void parseJSON(View view){
     * if(json_string == null) {
     * Toast.makeText(getApplicationContext(), "First Get JSON", Toast.LENGTH_SHORT).show();
     * }
     * else {
     * Intent intent = new Intent(MyProject.this, DisplayListView.class);
     * intent.putExtra("json_data", json_string);
     * startActivity(intent);
     * }
     * <p>
     * <p>
     * <p>
     * <p>
     * }
     **/

    public class BackgroundTask extends AsyncTask<Void, Void, String> {

        String project_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {
            project_url = "http://192.168.137.1/project6/project.php";
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(project_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            // TextView textView = (TextView)findViewById(R.id.textview);
            // textView.setText(result);
            json_string = result;

            if (json_string == null) {
                Toast.makeText(getApplicationContext(), "No able to get data", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MyProject.this, DisplayListView.class);
                intent.putExtra("json_data", json_string);
                startActivity(intent);
            }
        }
    }


    public class BackgroundTask2 extends AsyncTask<Void, Void, String> {

        String project_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {
            project_url = "http://192.168.137.1/project6/todolist.php";
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(project_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            // TextView textView = (TextView)findViewById(R.id.textview);
            // textView.setText(result);
            json_string = result;

            if (json_string == null) {
                Toast.makeText(getApplicationContext(), "No able to get data", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MyProject.this, ToDoList.class);
                intent.putExtra("json_data", json_string);
                startActivity(intent);
            }
        }
    }



}
