package com.example.textscanner;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.realm.Realm;

public class EditActivity2 extends AppCompatActivity {

    Button button_save;
    TextView titleView, descriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

//        if (getIntent().hasFileDescriptors())



        button_save = findViewById(R.id.save_btn);
        descriptionView = findViewById(R.id.descriptioninput);
        titleView = findViewById(R.id.titleinput);
//
//        Bundle descriptionTextIntent = getIntent().getExtras();
//        String description = descriptionTextIntent.getString("scantext");
//        long createdTime = System.currentTimeMillis();
//
//        descriptionView.setText(description);
//
//        Realm.init(getApplicationContext());
//        Realm realm = Realm.getDefaultInstance();
//
//        button_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                realm.beginTransaction();
//                Note note = realm.createObject(Note.class);
//
//                note.setCreatedTime(createdTime);
//                note.setTitle(titleView.getText().toString());
//                note.setDescription(descriptionView.getText().toString());
//                realm.commitTransaction();
//                Toast.makeText(EditActivity2.this, "Worked!", Toast.LENGTH_LONG);
//                goMainActivity();
//                finish();
//
//            }
//        });




    }
    private void goMainActivity(){
        Intent intent = new Intent(EditActivity2.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

//    Intent myIntent = getIntent(); // this is just for example purpose
// * myIntent.getExtra("key");