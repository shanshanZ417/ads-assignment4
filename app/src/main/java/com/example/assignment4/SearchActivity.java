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
    private Bundle bundle;
    String searchString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        bsearchBut = (Button) findViewById(R.id.searchBut);
        bsearchBox = (EditText) findViewById(R.id.searchBox);
        bsearchBut.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.searchBut:
                searchString = bsearchBox.getText().toString();
                bundle = getIntent().getExtras();
                bundle.putString("searchString", searchString);
                Intent intent = new Intent(this, SearchResultActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }
}
