package com.example.chewshwu.myproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by CHEW SHWU on 11/28/2016.
 */

public class ProjectMemberList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private ProjectMemberAdapter adapter;
    private List<ProjectMembers> projectMembersList;
    String projectID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectmembers);

        Bundle bundle = getIntent().getExtras();
        projectID = String.valueOf(bundle.getInt("projectID"));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        projectMembersList  = new ArrayList<>();
      //  load_data_from_server(bundle.getInt("projectID"));
    //    load_data_from_server(0);

        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new ProjectMemberAdapter(this, projectMembersList);
        recyclerView.setAdapter(adapter);

        new GetProjectMember().execute();

   /*     recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if(gridLayoutManager.findLastCompletelyVisibleItemPosition() == projectMembersList.size()-1){
                    load_data_from_server(projectMembersList.get(projectMembersList.size()-1).getUser_id());
                }

            }
        });*/

    }


    private class GetProjectMember extends AsyncTask<Void, Void, Void> {

        Context context;
        String project_url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            project_url = "http://192.168.137.1/project6/projectmembers.php";
            String JSON_STRING = getData(project_url);

            if (JSON_STRING != null) {

                try {

                    JSONObject jsonObject = new JSONObject(JSON_STRING);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                    int count = 0;
                    String name, email, position, imageurl;
                    int user_id;


                    while (count < jsonArray.length()) {
                        JSONObject JO = jsonArray.getJSONObject(count);
                        user_id = JO.getInt("user_id");
                        name = JO.getString("name");
                        email = JO.getString("email");
                        position = JO.getString("position");
                        imageurl = JO.getString("imageurl");

                        ProjectMembers projectMembers = new ProjectMembers(user_id, name, email, position, imageurl);
                        projectMembersList.add(projectMembers);
                        count++;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {

                Toast.makeText(getApplicationContext(), "Couldn't get json from server. Check LogCat for possible errors!", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
           adapter.notifyDataSetChanged();

        }
    }








    public String getData(String urlAddress) {

        HttpURLConnection httpURLConnection = Connector.connect(urlAddress);

        if (httpURLConnection == null) {
            return null;
        }

        InputStream inputStream = null;
        try {
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("projectID", "UTF-8") + "=" + URLEncoder.encode(projectID, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = null;
            StringBuffer stringBuffer = new StringBuffer();

            if (bufferedReader != null) {
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }

                bufferedReader.close();

            } else {
                return null;
            }

            return stringBuffer.toString();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }






}
