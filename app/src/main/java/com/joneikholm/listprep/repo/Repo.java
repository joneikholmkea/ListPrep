package com.joneikholm.listprep.repo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.joneikholm.listprep.model.Note;
import com.joneikholm.listprep.service.Updatable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Repo {
    private static FirebaseFirestore db;
    private static FirebaseStorage storage = FirebaseStorage.getInstance();
    private static Repo re = new Repo();
    private static Updatable mainActivity;
    private List<Note> notes = new ArrayList<>();

    public static void init(Updatable activity){
        db = FirebaseFirestore.getInstance();
        mainActivity = activity;
        re.startNoteListener();
    }

    public static Repo r(){
        return re;
    }

//    public void uploadImage()


    public void addNote(String txt){
        UUID uuid = UUID.randomUUID();
        DocumentReference noteRef = db.collection("notes").document(uuid.toString());
        Map<String,String> map = new HashMap<>();
        map.put("title", txt);
        noteRef.set(map);
    }

    public void updateNote(Note note, Bitmap currentBitmap){
        DocumentReference noteRef = db.collection("notes").document(note.getId());
        Map<String,String> map = new HashMap<>();
        map.put("title", note.getTitle());
        noteRef.set(map);
        if(currentBitmap != null){
            uploadBitmap(note, currentBitmap);
        }
    }

    private void uploadBitmap(Note note, Bitmap bitmap) {
        StorageReference ref = storage.getReference(note.getId());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        ref.putBytes(baos.toByteArray()).addOnCompleteListener(snap -> {
            System.out.println("OK to upload " + snap);
        }).addOnFailureListener(exception -> {
            System.out.println("failure to upload " + exception);
        });
    }

    public void downloadBitmap(String fileName, Updatable activity){
        StorageReference ref = storage.getReference(fileName);
        int max = 1024 * 1024;
        ref.getBytes(max).addOnSuccessListener(bytes -> {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0, bytes.length);
            activity.update(bitmap); // god linie!
        }).addOnFailureListener(ex -> {
            System.out.println("error in download " + ex);
        });
    }

    public void startNoteListener(){
        db.collection("notes").addSnapshotListener((values,e) ->{
            notes.clear();
            for(DocumentSnapshot snap : values.getDocuments()){
                if(snap.get("title") != null) {
                    Note note = new Note(snap.get("title").toString(), snap.getId());
                    System.out.println("received note, id: " + snap.getId());
                    notes.add(note);
                }
            }
        mainActivity.update(null);
        });
    }

    public void delete(int rowId) {
        DocumentReference noteRef = db.collection("notes").document(notes.get(rowId).getId());
        noteRef.delete();
        mainActivity.update(null);
    }

    public List<Note> getNotes() {
        return notes;
    }
}
