package com.example.assignment4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CoreFunctionActivity extends AppCompatActivity implements View.OnClickListener {
    Button buploadButton;
    Button bviewButton;
    Button bsearchButton;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core_function);
        buploadButton = (Button) findViewById(R.id.uploadButton);
        bviewButton = (Button) findViewById(R.id.viewButton);
        bsearchButton = (Button) findViewById(R.id.searchButton);


        buploadButton.setOnClickListener(this);
        bviewButton.setOnClickListener(this);
        bsearchButton.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.viewButton:
                Intent intentView = new Intent(this, ViewActivity.class);
                bundle = getIntent().getExtras();
                intentView.putExtras(bundle);
                startActivity(intentView);
                break;
            case R.id.searchButton:
                Intent intentSearch = new Intent(this, SearchActivity.class);
                bundle = getIntent().getExtras();
                intentSearch.putExtras(bundle);
                startActivity(intentSearch);
                break;

        }

    }
}
