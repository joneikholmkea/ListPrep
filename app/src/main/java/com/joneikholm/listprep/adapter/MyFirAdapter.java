package com.joneikholm.listprep.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.joneikholm.listprep.R;
import com.joneikholm.listprep.model.Note;

import java.util.ArrayList;
import java.util.List;

public class MyFirAdapter extends BaseAdapter {

    private List<Note> notes;
    private LayoutInflater layoutInflater;

    public MyFirAdapter(Context context, List<Note> n) {
        layoutInflater = LayoutInflater.from(context);
        notes = n;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int i) { // bliver ikke kaldt
        return notes.get(i);
    }

    @Override
    public long getItemId(int i) { // bliver kaldt 2 gange i starten
        System.out.println("getItemId called " + i);
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            System.out.println("view is NULL");
            view = layoutInflater.inflate(R.layout.myrow, null);
        }else {
             System.out.println("received view:  " + view.getClass().getName());
        }

        TextView textView = view.findViewById(R.id.myTextView);
        textView.setText(notes.get(i).getTitle());

//        ImageView imageView = view.findViewById(R.id.myImageView);
//        imageView.setImageResource(cars[i]);
        return view;
    }
}
