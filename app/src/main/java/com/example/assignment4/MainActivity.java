package com.example.assignment4;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button googleSigninBut;
    Button visitorLoginBut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        googleSigninBut = (Button) findViewById(R.id.googleSignin);
        visitorLoginBut = (Button) findViewById(R.id.visitorLogin);

        googleSigninBut.setOnClickListener(this);
        visitorLoginBut.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.googleSignin:
                Intent intentGoogle = new Intent(this, GoogleSignInActivity.class);
                startActivity(intentGoogle);
                break;
            case R.id.visitorLogin:
                Intent intentVisitor = new Intent(this, CoreFunctionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID", null);
                bundle.putBoolean("isAuthen", false);
                intentVisitor.putExtras(bundle);
                startActivity(intentVisitor);
                break;

        }
    }
}
