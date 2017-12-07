package com.example.assignment4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by misaki on 12/5/17.
 * INTRO:
 * adapter that store and show the information for each item in the given format
 * REFERENCE:
 * https://www.youtube.com/watch?v=akIrsTB2zIQ
 * https://stackoverflow.com/questions/39702304/retrieve-stored-image-from-firebase-storage
 */

public class ImageListAdapter extends ArrayAdapter<Photo> {
    public Activity context;
    public int resource;
    public List<Photo> listImage;
    // FireBase Storage settings
    StorageReference storageReference;
    ImageView img;
    public String views;
    public String stroPath;

    public ImageListAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<Photo> objects, @NonNull String views){
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        listImage = objects;
        this.views = views;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();

        View v = inflater.inflate(resource, null);
        TextView tvName = (TextView) v.findViewById(R.id.tvImageName);
        img = (ImageView) v.findViewById(R.id.imgView);

        Photo i = listImage.get(position);
        tvName.setText(i.getPhotoName());

        // check the type of image to be displayed -- original or processed photo
        if (views.equals("plain")){
            stroPath = "images/";
        } else {
            stroPath = "ascii-images/";
        }
        storageReference = FirebaseStorage.getInstance().getReference().child(stroPath + i.getPhotoId());
        final long ONE_MEGABYTE = 1024 * 1024;

        // fetch image from firebase storage with given photoId
        // display the image with bitmap
        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                img.setImageBitmap(bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });


        return v;
    }
}
