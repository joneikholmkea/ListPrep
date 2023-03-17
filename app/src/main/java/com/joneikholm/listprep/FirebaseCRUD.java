package com.joneikholm.listprep;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.joneikholm.listprep.adapter.MyFirAdapter;
import com.joneikholm.listprep.global.Global;
import com.joneikholm.listprep.model.Note;
import com.joneikholm.listprep.repo.Repo;
import com.joneikholm.listprep.service.Updatable;

public class FirebaseCRUD extends AppCompatActivity implements Updatable {

    private MyFirAdapter myAdapter;
    AlertDialog.Builder builder;
    private int idToDelete = -1; // initialize to -1 to indicate no index


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_c_r_u_d);
        builder = new AlertDialog.Builder(this);
        myAdapter = new MyFirAdapter(this, Repo.r().getNotes());
        ListView listView = findViewById(R.id.myFirListView);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener((parent, view, pos, id) ->{
            Intent intent = new Intent(this, DetailActivity_crud.class);

            Note note = Repo.r().getNotes().get((int)id);
            System.out.println("intent note: " + note.getId());
           // intent.putExtra("currentNote", note);
            Global.map.put(Global.NOTE_KEY, note);
            startActivity(intent);
        });

        listView.setOnItemLongClickListener((adapterView, view, i, rowId) -> {
            System.out.println("long click on row:" + rowId);
           // Repo.r().delete((int)rowId);
            idToDelete = (int)rowId;
            alertBeforeDelete();
            return true; // true, if this will consume the event.
        });
        Repo.init(this);
    }

        // Add this to YES, for confirmation.
        //  Toast.makeText(getApplicationContext(),"item deleted",
        //    Toast.LENGTH_SHORT).show();
    private void alertBeforeDelete(){
        builder.setMessage("Do you want to delete this item ?")
                .setPositiveButton("Yes", (dialog, id) -> {
                     Repo.r().delete(idToDelete);
                     idToDelete = -1; // reset value
                })
                .setNegativeButton("No", (dialog, id) -> {
                    //  Action for 'NO' Button
                    dialog.cancel();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }



    private void getArticles(MyFirAdapter myAdapter) {
        String result = "";
    }

    @Override
    public void update(Object o) {
        myAdapter.notifyDataSetChanged();
    }

    public void createNote(View view){
        Repo.r().addNote("Write away...");
    }
}