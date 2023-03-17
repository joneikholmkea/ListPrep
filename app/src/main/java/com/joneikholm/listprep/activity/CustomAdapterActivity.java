package com.joneikholm.listprep.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.joneikholm.listprep.util.MyJSONParser;
import com.joneikholm.listprep.R;
import com.joneikholm.listprep.adapter.MyAdapter;
import com.joneikholm.listprep.service.FetchData;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class CustomAdapterActivity extends AppCompatActivity {
    private FetchData fetchData;
    private MyJSONParser myJSONParser = new MyJSONParser();
    private String[] dataArr = new String[20];
    private String[] contentArr = new String[20];
    private String[] linkArr = new String[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_adapter);

        int[] cars = {R.drawable.car1, R.drawable.car2, R.drawable.car3, R.drawable.car4};
        MyAdapter myAdapter = new MyAdapter(this, dataArr, cars);
        ListView listView = findViewById(R.id.myListView2);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener((parent, view, pos, id) ->{
            Intent intent = new Intent(this, NewsDetailActivity.class );
            intent.putExtra("content", contentArr[(int)id]);
            intent.putExtra("url", linkArr[(int)id]);
            startActivity(intent);
        });
        getArticles(myAdapter);

    }

    private void getArticles(MyAdapter myAdapter) {
        fetchData = new FetchData();
        String result = "";
        try {
            result = fetchData.execute().get(); // will perform the internet query
            JSONObject jsonObject = myJSONParser.parse(result);
            JSONArray articles = (JSONArray) jsonObject.get("results");
            int count = 0;
            for(Object article : articles){
                JSONObject jsonArticle = (JSONObject)article;
                dataArr[count] = (String)jsonArticle.get("title");
               // contentArr[count] = (String)jsonArticle.get("content");
                linkArr[count] = (String)jsonArticle.get("link");
                count++;
            }
            myAdapter.notifyDataSetChanged(); // tells the adapter to refresh
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}