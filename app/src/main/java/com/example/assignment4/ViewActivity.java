package com.example.assignment4;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity {

    private DatabaseReference pubDatabaseRef;
    private DatabaseReference ownDatabaseRef;
    private List<Photo> imgList;
    private ListView lv;
    private ImageListAdapter adapter;
    private ProgressDialog progressDialog;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        imgList = new ArrayList<>();
        lv = findViewById(R.id.ListViewImage);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Waiting for the image loading....");
        progressDialog.show();

        pubDatabaseRef = FirebaseDatabase.getInstance().getReference("publicPhoto");
        bundle = getIntent().getExtras();
        if(bundle.getBoolean("isAuthen")) {
            ownDatabaseRef = FirebaseDatabase.getInstance().getReference("privateUser");
            ownDatabaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    progressDialog.dismiss();
                    DataSnapshot dataSnapshots = dataSnapshot.child(bundle.getString("userID"));
                    for (DataSnapshot snapshot : dataSnapshots.getChildren()) {
                        Photo img = snapshot.getValue(Photo.class);
                        imgList.add(img);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    progressDialog.dismiss();
                }
            });
        }

        pubDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Photo img = snapshot.getValue(Photo.class);
                    imgList.add(img);
                }
                //Init adapter
                adapter = new ImageListAdapter(ViewActivity.this, R.layout.image_item, imgList);
                //Set adapter for listview
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

    }
}
