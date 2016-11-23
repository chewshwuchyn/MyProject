package com.example.chewshwu.myproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by CHEW SHWU on 10/5/2016.
 */

public class TaskMain extends AppCompatActivity {

    String json_string;
    String sprojectString, sprojectID, sprojectName;
    //   EditText projectIDEt;
    Context ctx = this;
    String text;
    spinnerObjProj sop = new spinnerObjProj();

    ArrayList<HashMap<String, String>> spinnerobjpro = new ArrayList<HashMap<String, String>>();

    final static String urlAddress = "http://192.168.137.1/project6/getprojectlist.php";
    //  String projectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintask);

        Spinner sp = (Spinner) findViewById(R.id.spinner);





        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            ArrayList<HashMap<String, String>> spinnerobjpro = new ArrayList<HashMap<String, String>>();

            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedItemView, int position, long id) {
                sprojectString = parent.getSelectedItem().toString();


                sprojectString = sprojectString.substring(1, sprojectString.length() - 1);
                String[] choppedSprojectString = sprojectString.split("=");
                sprojectString = choppedSprojectString[2];
                sprojectName = choppedSprojectString[1];


                //      sprojectID = parent.getItemAtPosition(position).get("projectName").toString;
                //     sprojectName = spinnerobjpro.get(position).get("projectName").toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //    projectIDEt = (EditText) findViewById(R.id.etProjID);
        new spinnerDownloader(TaskMain.this, urlAddress, sp).execute();
    }

    public void ShowAllProject(View view) {
        new BackgroundTask2().execute();
    }

    public void ShowTask(View view) {
        //  projectID = projectIDEt.getText().toString();
        BackgroundTask3 backgroundTask3 = new BackgroundTask3();
        backgroundTask3.execute(sprojectString);
    }

    public void CreateNewTask(View view) {
        Intent intent = new Intent(TaskMain.this, CreateTask.class);
        //   projectID = projectIDEt.getText().toString();
        intent.putExtra("projectID", sprojectString);
        startActivity(intent);

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
                intent.putExtra("projectID", sprojectString);
                startActivity(intent);
            }
        }
    }

}





