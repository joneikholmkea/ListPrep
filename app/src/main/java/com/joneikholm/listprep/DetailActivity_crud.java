package com.joneikholm.listprep;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.joneikholm.listprep.global.Global;
import com.joneikholm.listprep.model.Note;
import com.joneikholm.listprep.repo.Repo;
import com.joneikholm.listprep.service.Updatable;

import java.io.InputStream;

public class DetailActivity_crud extends AppCompatActivity implements Updatable {

    private EditText editText;
    private Note currentNote; // you know, the note in question

    private ImageView myDetailImageView;
    private Bitmap currentBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_crud);
        editText = findViewById(R.id.editTextDetail);
        myDetailImageView = findViewById(R.id.myDetailImageView);

        currentNote = (Note) Global.map.get(Global.NOTE_KEY);
        System.out.println("current Note id: " + currentNote.getId());
        if(currentNote != null) {
            editText.setText(currentNote.getTitle());
            Repo.r().downloadBitmap(currentNote.getId(), this);
        }else {
            System.out.println("currentNote is NULL");
        }
    }

    public void save(View view){
        System.out.println("save pressed");
        currentNote.setTitle(editText.getText().toString());
        Repo.r().updateNote(currentNote, currentBitmap);

    }

    public void galleryBtnPressed(View view){
        Intent intent = new Intent(Intent.ACTION_PICK); // make an implicit intent, which will allow
        // the user to choose among different services to accomplish this task.
        intent.setType("image/*"); // we need to set the type of content to pick
        startActivityForResult(intent, 1); // start the activity, and in this case
        // expect an answer
    }

    public void cameraBtnPressed(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 2);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("back from image picking ");
        if(requestCode == 1) { // gallery
            backFromGallery(data);
        }
        if(requestCode == 2) { // camera
            backFromCamera(data);
        }
    }

    private void backFromGallery(@Nullable Intent data) {
        Uri uri = data.getData();
        try {
            InputStream is = getContentResolver().openInputStream(uri);
            currentBitmap = BitmapFactory.decodeStream(is);
            myDetailImageView.setImageBitmap(currentBitmap);

        }catch (Exception e){
        }
    }

    private void backFromCamera(@Nullable Intent data) {
        try {
            currentBitmap = (Bitmap)data.getExtras().get("data");
            myDetailImageView.setImageBitmap(currentBitmap);
        }catch (Exception e){

        }
    }


    @Override
    public void update(Object o) {
        Bitmap bitmap = (Bitmap)o;
        if(bitmap!= null){
            runOnUiThread(()->{
                myDetailImageView.setImageBitmap(bitmap);
            });
        }
    }
}