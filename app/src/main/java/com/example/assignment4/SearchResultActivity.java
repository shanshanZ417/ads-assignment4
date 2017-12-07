package com.example.assignment4;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {
    String searchString;
    private DatabaseReference pubDatabaseRef;
    private DatabaseReference ownDatabaseRef;
    private List<Photo> searchList;
    private ListView lv;
    private ImageListAdapter adapter;
    private ProgressDialog progressDialog;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        searchList  = new ArrayList<>();
        lv = findViewById(R.id.ListSearchImage);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Waiting for searching....");
        progressDialog.show();

        pubDatabaseRef = FirebaseDatabase.getInstance().getReference("publicPhoto");
        bundle = getIntent().getExtras();
        searchString = bundle.getString("searchString");

        if(bundle.getBoolean("isAuthen")) {
            ownDatabaseRef = FirebaseDatabase.getInstance().getReference("privateUser");
            ownDatabaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    progressDialog.dismiss();
                    String userId = bundle.getString("userID");
                    if (userId != null){
                        DataSnapshot dataSnapshots = dataSnapshot.child(userId);
                        for (DataSnapshot snapshot : dataSnapshots.getChildren()) {
                            Photo img = snapshot.getValue(Photo.class);
                            if (img.getDescription().toLowerCase().contains(searchString.toLowerCase())){
                                System.out.println("Here is the key word result!!!!!!" + img.getPhotoName());
                                searchList.add(img);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    progressDialog.dismiss();
                }
            });
        }
        pubDatabaseRef = FirebaseDatabase.getInstance().getReference("publicPhoto");
        pubDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Photo img = snapshot.getValue(Photo.class);
                    if (img.getDescription().toLowerCase().contains(searchString.toLowerCase())){
                        searchList.add(img);
                    }
                }
                //Init adapter
                adapter = new ImageListAdapter(SearchResultActivity.this, R.layout.image_item, searchList, "plain");
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
