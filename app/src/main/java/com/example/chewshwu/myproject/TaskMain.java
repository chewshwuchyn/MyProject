package com.example.chewshwu.myproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by CHEW SHWU on 10/5/2016.
 */

public class TaskMain extends AppCompatActivity {

    String json_string;
    String projectID;
    EditText projectIDEt;
    Context ctx = this;
    //  String projectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintask);


        projectIDEt = (EditText) findViewById(R.id.etProjID);

    }

    public void ShowAllProject(View view) {
        new BackgroundTask2().execute();
    }

    public void ShowTask(View view){
        projectID = projectIDEt.getText().toString();
        BackgroundTask3 backgroundTask3 = new BackgroundTask3();
        backgroundTask3.execute(projectID);
    }


    public class BackgroundTask2 extends AsyncTask<Void, Void, String> {
        String project_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {
            project_url = "http://192.168.137.1/project6/task.php";
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
                Intent intent = new Intent(TaskMain.this, TaskListView.class);
                intent.putExtra("json_data", json_string);
                startActivity(intent);
            }
        }


    }

    public class BackgroundTask3 extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String JSON_STRING;

        @Override
        protected String doInBackground(String... params) {
            String projectID = params[0];
            String register_url = "http://192.168.137.1/project6/taskdetail.php";

            try {

                URL url = new URL(register_url);
                String urlParams = "projectID=" + projectID;
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(urlParams.getBytes());
                outputStream.flush();
                outputStream.close();
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
                return "Exception: " + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            json_string = result;

            if (json_string == null) {
                Toast.makeText(getApplicationContext(), "No able to get data", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(TaskMain.this, TaskDetailList.class);
                intent.putExtra("json_data", json_string);
                intent.putExtra("projectID", projectID);
                startActivity(intent);
            }
        }
        }

}





