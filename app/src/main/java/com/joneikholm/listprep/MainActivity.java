package com.joneikholm.listprep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.joneikholm.listprep.repo.Repo;
import com.joneikholm.listprep.service.Updatable;

public class MainActivity extends AppCompatActivity implements Updatable {

    private String[] countries = {"USA", "Danmark", "Norge", "Frankrig"};
    private TextView editText;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText1);
        adapter = new ArrayAdapter<>(this,  R.layout.myrow, R.id.myTextView,  countries);
        ListView listView = findViewById(R.id.myListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((AdapterView<?> _listView, View linearLayout, int adapterPos, long rowId) -> {
            LinearLayout linearLayout1 = (LinearLayout) linearLayout;
            ListView listView1 = (ListView)_listView;
            String row = (String)listView1.getItemAtPosition(adapterPos);
        });
        Repo.init(this);

    }

    public void saveText(View view){
        String txt = editText.getText().toString();
        Repo.r().addNote(txt);
    }

    @Override
    public void update(Object o) {
        adapter.notifyDataSetChanged();
    }
}