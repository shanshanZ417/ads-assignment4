package com.example.assignment4;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int GALLERY_REQUEST= 1;
    // FireBase Storage settings
    FirebaseStorage storage;
    StorageReference storageReference;
    // FireBase Database settings
    FirebaseDatabase database;
    DatabaseReference publicRef;
    DatabaseReference privateRef;

    ImageView imageToUpload;
    Button uploadImageBut;
    EditText uploadImageName, uploadImageDes;
    CheckBox isPrivate;
    Uri selectedImage;

    String imageId;
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        // FireBase Database Init
        database = FirebaseDatabase.getInstance();

        // FireBase Storage Init
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        imageToUpload = (ImageView) findViewById(R.id.imageToUpload);
        uploadImageBut = (Button) findViewById(R.id.uploadImage);
        uploadImageName = (EditText) findViewById(R.id.nameOfImage);
        uploadImageDes = (EditText) findViewById(R.id.descriptionOfImage);
        isPrivate = (CheckBox) findViewById(R.id.isPrivate);

        imageToUpload.setOnClickListener(this);
        uploadImageBut.setOnClickListener(this);


    }
    private void writeToDatabase(Photo photo){
        publicRef = database.getReference("publicPhoto");
        privateRef = database.getReference("privateUser");
        if (photo.isPrivate){
            privateRef.child(photo.userId).push().setValue(photo);
        } else {
            publicRef.push().setValue(photo);
        }
    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.imageToUpload:
                Intent openGallary = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallary, GALLERY_REQUEST);
                break;
            case R.id.uploadImage:
                if (selectedImage != null) {
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();
                    imageId = UUID.randomUUID().toString();
                    StorageReference ref = storageReference.child("images/" + imageId);
                    ref.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(UploadActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            url =  taskSnapshot.getDownloadUrl().toString();
                            System.out.println("Here is the url !!!@@@@@@@@@" + url);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(UploadActivity.this, "Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
                }
                String imagename = uploadImageName.getText().toString();
                String imagedescription = uploadImageDes.getText().toString();
                boolean isprivate = isPrivate.isChecked();
                Bundle bundles = getIntent().getExtras();
                String userid = bundles.getString("userID");
                System.out.println("The user id is !!!!!!!!!!!!" + userid);
                boolean isAuthen = bundles.getBoolean("isAuthen");

                Photo photo = new Photo(imageId, userid, imagename, imagedescription, isprivate, url);
                writeToDatabase(photo);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST  && resultCode == RESULT_OK && data != null){
            selectedImage = data.getData();
            imageToUpload.setImageURI(selectedImage);
        }
    }
}


















