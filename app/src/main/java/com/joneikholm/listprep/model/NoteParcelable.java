package com.joneikholm.listprep.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class NoteParcelable implements Parcelable {

    private String title;
    private String id = UUID.randomUUID().toString();

    public NoteParcelable(String title, String id) {
        this.title = title;
        if(id != null){
            this.id = id;
        }
    }

    public NoteParcelable(String title) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(id);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Creator<NoteParcelable> CREATOR = new Creator<NoteParcelable>() {
        public NoteParcelable createFromParcel(Parcel in) {
            return new NoteParcelable(in);
        }

        public NoteParcelable[] newArray(int size) {
            return new NoteParcelable[size];
        }
    };
    // example constructor that takes a Parcel and gives you an object populated with it's values
    private NoteParcelable(Parcel in) {
        title = in.readString(); // intelligent: will find the title in the Parcel by itself.
        id = in.readString();
    }
}
