package com.example.chewshwu.myproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;



import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {
    EditText EmailEt, PasswordEt;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EmailEt = (EditText)findViewById(R.id.etEmail);
        PasswordEt = (EditText) findViewById(R.id.etPassword);


    }

    public void OnLogin(View view){
        email = EmailEt.getText().toString();
        password = PasswordEt.getText().toString();
        String type = "login";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, email, password);


    }


    public void OpenRegister(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public class BackgroundWorker extends AsyncTask<String, Void, String> {
        Context context;
        ProgressDialog progressDialog;
        AlertDialog alertDialog;
        BackgroundWorker (Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "http://192.168.137.1/project6/login.php";
            if(type.equals("login")){
                try{
                    String email = params[1];
                    String password = params[2];
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                    String result="";
                    String line;
                    while((line = bufferedReader.readLine())!= null) {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();


                    return result;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();;
            //   progressDialog = ProgressDialog.show(LoginActivity.this, "Loading...", null, true, true);
            //    alertDialog = new AlertDialog.Builder(context).create();
            //    alertDialog.setTitle("Login Status");


        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("")) {
                result = "Login not success.";
                //    alertDialog.setMessage(result);
                //     alertDialog.show();

            }else{
                //    alertDialog.setMessage(result);
                //    alertDialog.show();
                Intent intent = new Intent(LoginActivity.this, MyProject.class);
                intent.putExtra("result", result);
              //  intent.putExtra("email", email);
               // intent.putExtra("password", password);
                LoginActivity.this.startActivity(intent);

            }
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();

            //   Intent intent = new Intent(LoginActivity.this, MyProject.class);
            //   intent.putExtra("email", email);
            //  intent.putExtra("password", password);
            //  LoginActivity.this.startActivity(intent);


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        /**    if (success) {
         String name = jsonResponse.getString("name");
         String email = jsonResponse.getString("email");

         Intent intent = new Intent(LoginActivity.this, MyProject.class);
         intent.putExtra("name", name);
         intent.putExtra("email", email);
         LoginActivity.this.startActivity(intent);
         } else {
         AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
         builder.setMessage("Login Failed")
         .setNegativeButton("Retry", null)
         .create()
         .show();
         }**/
    }
}
