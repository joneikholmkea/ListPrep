package com.joneikholm.listprep.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.joneikholm.listprep.R;

public class NewsDetailActivity extends AppCompatActivity {

    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView textView = findViewById(R.id.myTextView2);
        String txt = getIntent().getStringExtra("content");
        url = getIntent().getStringExtra("url");
        textView.setText(txt);
    }

    public void goToWebpage(View view){
        Intent i = new Intent(Intent.ACTION_VIEW); // not asking for a specific class, as we talked about.
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}