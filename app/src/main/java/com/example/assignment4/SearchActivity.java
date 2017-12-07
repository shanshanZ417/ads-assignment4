package com.example.assignment4;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by misaki on 12/5/17.
 * INTRO:
 * pages that let user enter the keywords for search function
 */

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
            // search button, forward to search page(with entered key word)
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
