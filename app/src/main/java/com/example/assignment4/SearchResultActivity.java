package com.example.assignment4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class SearchResultActivity extends AppCompatActivity {
    EditText bsearchResult;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        bsearchResult = (EditText) findViewById(R.id.searchResult);
        bundle = getIntent().getExtras();
        if(bundle.getBoolean("searchExist")) {
            bsearchResult.setText(bundle.getString("searchResult"));
        } else{
            bsearchResult.setText("There is no result! Please enter other keyword!");
        }

    }
}
