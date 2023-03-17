package com.joneikholm.listprep.service;

import android.os.AsyncTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchData extends AsyncTask<String,Void,String> {

    private String url = "https://newsdata.io/api/1/news?apikey=pub_19053672a88b2a19505c6b4b536a1a0bc3332&country=dk";
//    private String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=a3e688609c5940a98dc8df7713251264";
    private String result = "";
    HttpURLConnection urlConnection;
    @Override
    protected String doInBackground(String... strings) {
        System.out.println("calling doInBackground");
        try {
            URL url = new URL(this.url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            System.out.println("L24 " + urlConnection.getResponseMessage());
            System.out.println("L24 " + urlConnection.getResponseCode());
            InputStream in = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            int data = reader.read();
            while(data != -1){
                char c = (char)data;
                result += c;
                data = reader.read();
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed " + e.getMessage();
        } finally {
            urlConnection.disconnect();
        }

    }
}
