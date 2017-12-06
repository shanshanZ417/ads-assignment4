package com.example.assignment4;

import android.app.Activity;
import android.content.Context;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by misaki on 12/5/17.
 */

public class ImageListAdapter extends ArrayAdapter<Photo> {
    public Activity context;
    public int resource;
    public List<Photo> listImage;
    // FireBase Storage settings
    StorageReference storageReference;

    public ImageListAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<Photo> objects){
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        listImage = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();

        View v = inflater.inflate(resource, null);
        TextView tvName = (TextView) v.findViewById(R.id.tvImageName);
        ImageView img = (ImageView) v.findViewById(R.id.imgView);

        tvName.setText(listImage.get(position).getPhotoName());
//        storageReference = FirebaseStorage.getInstance().getReference().child("images/" + listImage.get(position).getPhotoId());
//        System.out.println("hehehhehe the storage place is??????????" + "images/" + listImage.get(position).getPhotoId());
        Glide.with(context).load(listImage.get(position).getUrl()).into(img);
        Glide.with(context).load(storageReference).into(img);


        return v;
    }
}
