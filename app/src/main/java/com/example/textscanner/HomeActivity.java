package com.example.textscanner;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.io.IOException;

//import com.canhub.cropper.CropImage;

public class HomeActivity extends AppCompatActivity {

    Button button_capture, button_copy, button_edit, button_save;
    TextView textView;
    private static int REQUEST_CAMERA_CODE = 100;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        button_capture = findViewById(R.id.button_capture);
        button_copy = findViewById(R.id.button_copy);
        button_save = findViewById(R.id.button_save);
        button_edit = findViewById(R.id.button_edit);
        textView = findViewById(R.id.textView);


        // to get permission for camera
        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{
                    Manifest.permission.CAMERA
            },REQUEST_CAMERA_CODE);
        }

        button_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(HomeActivity.this);

            }


        });

        button_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String scannedText = textView.getText().toString();
                copyToClipBoard(scannedText);
            }
        });

        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String scannedText = textView.getText().toString();
                goToEditTextActivity(scannedText);

            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
                goMainActivity();

            }
        });




    }

    void saveNote(){
        String title = "New Scan Text";
        String description = textView.getText().toString();

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


        documentReference = Utility.getCollectionReferenceForNotes().document();


        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(HomeActivity.this, "Added Note Successful!", Toast.LENGTH_LONG);
                }else{
                    Toast.makeText(HomeActivity.this, "Failed to Add Note", Toast.LENGTH_LONG);
                }
            }
        });
    }

    private void goMainActivity(){
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    getTextFromImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    private void getTextFromImage(Bitmap bitmap){
        TextRecognizer recognizer = new TextRecognizer.Builder(this).build();
        Toast.makeText(HomeActivity.this, "HERE!", Toast.LENGTH_SHORT);
        if (!recognizer.isOperational()) {
            Toast.makeText(HomeActivity.this, "Error Ocurred in getTextFromImage!", Toast.LENGTH_SHORT);
        }else{
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> textBlockSparseArray = recognizer.detect(frame);
            StringBuilder stringBuilder = new StringBuilder();

            for (int i=0; i<textBlockSparseArray.size(); i++){
                TextBlock textBlock = textBlockSparseArray.valueAt(i);
                stringBuilder.append(textBlock.getValue());
                stringBuilder.append(textBlock.getValue());
                stringBuilder.append("\n");
            }
            textView.setText(stringBuilder.toString());
            button_capture.setText("Retake");
            button_copy.setVisibility(View.VISIBLE);
        }
    }

    public void copyToClipBoard(String text){
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("Copied data", text);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(HomeActivity.this, "Copied to clipboard!", Toast.LENGTH_SHORT);
    }

    private void goToEditTextActivity(String text){
        Intent intent = new Intent(HomeActivity.this, EditActivity.class);
        intent.putExtra("scantext", text);
        startActivity(intent);
    }
}


/**
 * Activity A
 *
 * Intent myIntent = new Intent(ActivityA.this, Activityb.class);
 * myIntent.putExtra("key", "stringTextView");
 * startActivity(myIntent);
 * ------------------------------------------------------------------
 *
 * Activity B
 *
 * Intent myIntent = getIntent(); // this is just for example purpose
 * myIntent.getExtra("key");
 *
 */