package com.example.assignment4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Toast;

/**
 * Created by misaki on 12/5/17.
 * INTRO:
 * The core function page with four core functions: image upload, original image view, processed image view and search image
 */

public class CoreFunctionActivity extends AppCompatActivity implements View.OnClickListener {
    Button buploadButton;
    Button bviewButton;
    Button bsearchButton;
    Button bfilterButton;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core_function);
        buploadButton = (Button) findViewById(R.id.uploadButton);
        bviewButton = (Button) findViewById(R.id.viewButton);
        bsearchButton = (Button) findViewById(R.id.searchButton);
        bfilterButton = (Button) findViewById(R.id.filterButton);


        buploadButton.setOnClickListener(this);
        bviewButton.setOnClickListener(this);
        bsearchButton.setOnClickListener(this);
        bfilterButton.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // upload button, forward to upload page
            case R.id.uploadButton:
                Intent intentUpload = new Intent(this, UploadActivity.class);
                bundle = getIntent().getExtras();
                if(bundle.getBoolean("isAuthen")){
                    intentUpload.putExtras(bundle);
                    startActivity(intentUpload);
                } else {
                    Toast.makeText(CoreFunctionActivity.this, "Sorry, You should login in order to upload photo!", Toast.LENGTH_LONG).show();
                }
                break;
            // view button, forward to view page(original picture)
            case R.id.viewButton:
                Intent intentView = new Intent(this, ViewActivity.class);
                bundle = getIntent().getExtras();
                bundle.putString("view", "plain");
                intentView.putExtras(bundle);
                startActivity(intentView);
                break;
            // search button, forward to search page
            case R.id.searchButton:
                Intent intentSearch = new Intent(this, SearchActivity.class);
                bundle = getIntent().getExtras();
                intentSearch.putExtras(bundle);
                startActivity(intentSearch);
                break;
            // filter button, forward to view page(filtered picture)
            case R.id.filterButton:
                Intent intentFilter = new Intent(this, ViewActivity.class);
                bundle = getIntent().getExtras();
                bundle.putString("view", "filter");
                intentFilter.putExtras(bundle);
                startActivity(intentFilter);
                break;

        }

    }
}
