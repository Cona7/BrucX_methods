package com.javacodegeeks.androidhttppostgetexample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.accounts.Account;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AndroidHttpPostGetActivity extends Activity {
    MediaType JSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        getStudentById("AFF53F0B");

    }

    private void getAllStudents() {

        final String login = "cona";
        final String password = "cona123";
        String credential = Credentials.basic(login, password);

        final Request request = new Request.Builder()
                .url("http://tomcat.marinpetrunic.com:80/brucx-ws/api/v1/students")
                .header("Authorization", credential)
                .build();

        ApiHelper.getInstance().getOkHttpClient().newCall(request).enqueue(getAllStudentsCallBack);

    }

    Callback getAllStudentsCallBack = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {Log.e("cona", e.getMessage());}

        @Override
        public void onResponse(Call call, Response response) throws IOException {

            List<Studenti> studenti = new ArrayList<>();

            String jsonResponse = response.body().string();

            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(jsonResponse);
            JsonArray students = jsonElement.getAsJsonArray();

            Gson gson = new Gson();
            for (JsonElement object : students) {
                studenti.add(gson.fromJson(object, Studenti.class));
            }
            //User retval=users.get();
            Log.d("cona", "studenti:");
            Log.d("cona", studenti.toString());
            Log.d("cona", "fail");
        }
    };


    private void getStudentById(String id){


        final String login = "cona";
        final String password = "cona123";
        String credential = Credentials.basic(login, password);

        final Request request = new Request.Builder()
                .url("http://tomcat.marinpetrunic.com:80/brucx-ws/api/v1/students/"+id+"")
                .header("Authorization", credential)
                .build();

        ApiHelper.getInstance().getOkHttpClient().newCall(request).enqueue(getStudentByIdCallBack);
    }

    Callback getStudentByIdCallBack = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {Log.e("cona", e.getMessage());}

        @Override
        public void onResponse(Call call, Response response) throws IOException {

            String jsonResponse = response.body().string();

            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(jsonResponse);

                    Log.d("cona", jsonElement.toString());
        }
    };

    private void buyTicket(String id) {

        final String login = "cona";
        final String password = "cona123";
        String credential = Credentials.basic(login, password);


        JsonObject json = new JsonObject();
        json.addProperty("type", "test-karta" );
        json.addProperty("price", 20);
        String jsonString = json.toString();
        RequestBody body = RequestBody.create(JSON, jsonString);


        final Request request = new Request.Builder()
                .url("http://tomcat.marinpetrunic.com:80/brucx-ws/api/v1/students/"+id+"/tickets")
                .header("Authorization", credential)
                .post(body)
                .build();


        ApiHelper.getInstance().getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("cona", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    //success
                    Log.d("cona", response.body().string());


                } else {
                    Log.d("cona", "fail");
                    //failure
                }
            }
        });

    }

    private void deleteTicket(String id){

        final String login = "cona";
        final String password = "cona123";
        String credential = Credentials.basic(login, password);

        final Request request = new Request.Builder()
                .method("DELETE", null)
                .url("http://tomcat.marinpetrunic.com:80/brucx-ws/api/v1/students/"+id+"/tickets/test-karta")
                .header("Authorization", credential)
                .build();

        ApiHelper.getInstance().getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("cona", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    //success
                    Log.d("cona", response.body().string());
                } else {
                    Log.d("cona", "fail");
                    //failure
                }
            }
        });

    }

    private void useTicket(String id){

        final String login = "cona";
        final String password = "cona123";
        String credential = Credentials.basic(login, password);

        JsonObject json = new JsonObject();
        json.addProperty("used", 1 );
        String jsonString = json.toString();
        RequestBody body = RequestBody.create(JSON, jsonString);

        final Request request = new Request.Builder()
                .url("http://tomcat.marinpetrunic.com:80/brucx-ws/api/v1/students/"+id+"/tickets/test-karta")
                .header("Authorization", credential)
                .put(body)
                .build();

        ApiHelper.getInstance().getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("cona", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    //success
                    Log.d("cona", response.body().string());
                } else {
                    Log.d("cona", "fail");
                    //failure
                }
            }
        });
    }
}