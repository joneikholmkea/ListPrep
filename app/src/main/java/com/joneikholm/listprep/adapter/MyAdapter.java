package com.joneikholm.listprep.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.joneikholm.listprep.R;

public class MyAdapter extends BaseAdapter {

    private String[] countries;
    private int[] cars;
    private LayoutInflater layoutInflater;

    public MyAdapter(Context context, String[] c, int[] _cars) {
        layoutInflater = LayoutInflater.from(context);
        countries = c;
        cars = _cars;
    }

    @Override
    public int getCount() {
        return countries.length;
    }

    @Override
    public Object getItem(int i) { // bliver ikke kaldt
        return countries[i];
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
        textView.setText(countries[i]);
//        ImageView imageView = view.findViewById(R.id.myImageView);
//        imageView.setImageResource(cars[i]);
        return view;
    }
}
