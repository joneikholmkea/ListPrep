package com.joneikholm.listprep.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class Note {

    private String title;
    private String id = UUID.randomUUID().toString();

    public Note(String title, String id) {
        this.title = title;
        if(id != null){
            this.id = id;
        }
    }

    public Note(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
