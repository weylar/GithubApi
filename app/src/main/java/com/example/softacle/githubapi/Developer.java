package com.example.softacle.githubapi;
/**
 * Created by muilat on 14-Aug-17.
 */

public class Developer {

    //username of te developer
    String mUsername ;

    //developer image
    String mImageResource;

    //url that leads to te developer profile on webrowser
    String mProfileUrl;

    Developer(String username, String imageResource, String profileUrl){
        mUsername = username;
        mImageResource = imageResource;
        mProfileUrl = profileUrl;
    }

    //return username
    public String getUsername(){
        return mUsername;
    }
    //Return the image
    public String getImageResource(){
        return mImageResource;
    }
    //Return profile url
    public String getProfileUrl(){
        return mProfileUrl;
    }
}
