package com.example.assignment4;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    Button bsearchBut;
    EditText bsearchBox;
    private DatabaseReference pubDatabaseRef;
    private DatabaseReference ownDatabaseRef;
    private FirebaseDatabase database;
    String searchString;
    private ProgressDialog progressDialog;
    private List<String> searchList;
    private Bundle bundle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        bsearchBut = (Button) findViewById(R.id.searchBut);
        bsearchBox = (EditText) findViewById(R.id.searchBox);
        searchList = new ArrayList<>();

        bsearchBut.setOnClickListener(this);

//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Waiting for the searching result....");
//        progressDialog.show();
    }
    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.searchBut:
                database = FirebaseDatabase.getInstance();
                searchString = bsearchBox.getText().toString();
                bundle = getIntent().getExtras();
                if(bundle.getBoolean("isAuthen")) {
                    ownDatabaseRef = FirebaseDatabase.getInstance().getReference("privateUser");
                    ownDatabaseRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            progressDialog.dismiss();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Photo img = snapshot.getValue(Photo.class);
                                if (img.getDescription().toLowerCase().contains(searchString.toLowerCase())){
                                    searchList.add(img.getPhotoName());
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
                                searchList.add(img.getPhotoName());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        progressDialog.dismiss();
                    }
                });
                Bundle bundle = new Bundle();
                if (searchList.size() == 0){
                    bundle.getBoolean("searchExist", false);
                } else {
                    bundle.putString("searchResult", searchList.get(0));
                    bundle.putBoolean("searchExist", true);
                }
                Intent intent = new Intent(this, SearchResultActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }
}
