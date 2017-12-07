package com.example.assignment4;

/**
 * Created by misaki on 12/5/17.
 * INTRO:
 * Self-defined Photo class for storing the photo image
 */

public class Photo {
    public String photoId;
    public String userId;
    public String photoName;
    public String description;
    public boolean isPrivate;
    public String url;
    public Photo(String photoId, String userId, String photoName, String description, boolean isPrivate, String url){
        this.photoId = photoId;
        this.userId = userId;
        this.photoName = photoName;
        this.description = description;
        this.isPrivate = isPrivate;
        this.url = url;
    }
    public Photo(){

    }
    public String getPhotoName(){
        return this.photoName;
    }
    public String getDescription(){
        return this.description;
    }
    public String getPhotoId(){
        return this.photoId;
    }
    public String getUrl(){
        return this.url;
    }
}
