package com.example.textscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button scanbtn = findViewById(R.id.scanbtn);
        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHomeActivity();
            }
        });

        recyclerView = findViewById(R.id.recyclerview);
        setUpRecyclerView();

//        Realm.init(getApplicationContext());
//        Realm realm = Realm.getDefaultInstance();
//
//        RealmResults<Note> notesList = realm.where(Note.class).findAll().sort("createdTime", Sort.DESCENDING);
//        MyAdapter myAdapter = new MyAdapter(getApplicationContext(),notesList);
//        recyclerView.setAdapter(myAdapter);
//
//        notesList.addChangeListener(new RealmChangeListener<RealmResults<Note>>() {
//            @Override
//            public void onChange(RealmResults<Note> notes) {
//                myAdapter.notifyDataSetChanged();
//            }
//        });




    }

    void setUpRecyclerView(){
        Query query = Utility.getCollectionReferenceForNotes().orderBy("createdTime", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query, Note.class).build();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(options, this);
        recyclerView.setAdapter(noteAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        noteAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        noteAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        noteAdapter.notifyDataSetChanged();
    }

    private void goToHomeActivity() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
//
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.widget.ImageView;
//import android.content.Intent;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.view.View;
//import android.net.Uri;
//
//import android.support.annotation.NonNull;
//
//import com.google.firebase.ml.vision.common.FirebaseVisionImage;
//import com.google.firebase.ml.vision.text.FirebaseVisionText;
//import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;
//import com.google.firebase.ml.vision.FirebaseVision;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.OnFailureListener;
//
//public class MainActivity extends BaseActivity implements View.OnClickListener {
//
//    private Bitmap myBitmap;
//    private ImageView myImageView;
//    private TextView myTextView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        myTextView = findViewById(R.id.textView);
//        myImageView = findViewById(R.id.imageView);
//        findViewById(R.id.checkText).setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.checkText:
//                if (myBitmap != null) {
//                    runTextRecog();
//                }
//                break;
//
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case WRITE_STORAGE:
//                    checkPermission(requestCode);
//                    break;
//                case SELECT_PHOTO:
//                    Uri dataUri = data.getData();
//                    String path = MyHelper.getPath(this, dataUri);
//                    if (path == null) {
//                        myBitmap = MyHelper.resizePhoto(photo, this, dataUri, myImageView);
//                    } else {
//                        myBitmap = MyHelper.resizePhoto(photo, path, myImageView);
//                    }
//                    if (myBitmap != null) {
//                        myTextView.setText(null);
//                        myImageView.setImageBitmap(myBitmap);
//                    }
//                    break;
//
//            }
//        }
//    }
//
//    private void runTextRecog() {
//        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(myBitmap);
//        FirebaseVisionTextDetector detector = FirebaseVision.getInstance().getVisionTextDetector();
//        detector.detectInImage(image).addOnSuccessListener(new OnSuccessListener&lt;FirebaseVisionText&gt;() {
//            @Override
//            public void onSuccess(FirebaseVisionText texts) {
//                processExtractedText(texts);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure
//                    (@NonNull Exception exception) {
//                Toast.makeText(MainActivity.this,
//                        "Exception", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    private void processExtractedText(FirebaseVisionText firebaseVisionText) {
//        myTextView.setText(null);
//        if (firebaseVisionText.getBlocks().size() == 0) {
//            myTextView.setText(R.string.no_text);
//            return;
//        }
//        for (FirebaseVisionText.Block block : firebaseVisionText.getBlocks()) {
//            myTextView.append(block.getText());
//
//        }
//    }
//
//}