package com.example.textscanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.nio.charset.StandardCharsets;

import io.realm.Realm;

public class EditActivity extends AppCompatActivity {

    Button button_save;
    TextView titleView, descriptionView;
    String title, content, docId;
    boolean isEditMode=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

//        if (getIntent().hasFileDescriptors())



        button_save = findViewById(R.id.save_btn);
        descriptionView = findViewById(R.id.descriptioninput);
        titleView = findViewById(R.id.titleinput);

//        Bundle descriptionTextIntent = getIntent().getExtras();
//        String description = descriptionTextIntent.getString("scantext");
//        long createdTime = System.currentTimeMillis();
//
//        descriptionView.setText(description);
//
//        Realm.init(getApplicationContext());
//        Realm realm = Realm.getDefaultInstance();

        // receive data
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("description");
        docId = getIntent().getStringExtra("docId");

        if (docId!=null && !docId.isEmpty()){
            isEditMode = true;
        }


        titleView.setText(title);
        descriptionView.setText(content);


        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
//                realm.beginTransaction();
//                Note note = realm.createObject(Note.class);
//
//                note.setCreatedTime(Timestamp.now());
//                note.setTitle(titleView.getText().toString());
//                note.setDescription(descriptionView.getText().toString());
//                realm.commitTransaction();
//                Toast.makeText(EditActivity.this, "Worked!", Toast.LENGTH_LONG);
                goMainActivity();
                finish();

            }
        });
    }

    void saveNote(){
        String title = titleView.getText().toString();
        String description = descriptionView.getText().toString();

        Note note = new Note();

        if (title.trim().isEmpty()){
            note.setTitle("New Scan Text");
        }else{
            note.setTitle(title);
        }

        note.setDescription(description);
        note.setCreatedTime(Timestamp.now());

        saveNoteToFirebase(note);
    }

    void saveNoteToFirebase(Note note){
        DocumentReference documentReference;

        if (isEditMode) {
            // update the note
            documentReference = Utility.getCollectionReferenceForNotes().document(docId);
        }else{
            // create new note
            documentReference = Utility.getCollectionReferenceForNotes().document();
        }

        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(EditActivity.this, "Added Note Successful!", Toast.LENGTH_LONG);
                }else{
                    Toast.makeText(EditActivity.this, "Failed to Add Note", Toast.LENGTH_LONG);
                }
            }
        });
    }

    private void goMainActivity(){
        Intent intent = new Intent(EditActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

//    Intent myIntent = getIntent(); // this is just for example purpose
// * myIntent.getExtra("key");