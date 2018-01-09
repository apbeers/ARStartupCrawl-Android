package com.example.arstartupcrawl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Keep;
import android.util.Base64;

/**
 * Created by shawn on 1/7/2018.
 */

@Keep
public class Startup {


    private String description;
    private String startup_id;
    private double latitude;
    private String logobase64;
    private double longitude;
    private String snippet;
    private String title;
    private String url;

    @Keep
    public Startup() {

    }

    @Keep
    public Startup(String description, String startup_id, double latitude, String logobase64, double longitude, String snippet, String title, String url) {
        this.description = description;
        this.startup_id = startup_id;
        this.latitude = latitude;
        this.logobase64 = logobase64;
        this.longitude = longitude;
        this.snippet = snippet;
        this.title = title;
        this.url = url;
    }

    @Keep
    public Bitmap getBitmap() {
        byte[] bytes = Base64.decode(this.logobase64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    //@Keep
    //public LatLng getLatLng() {
       // return new LatLng(latitude, longitude);
    //}

    @Keep
    public String getDescription() {
        return description;
    }

    @Keep
    public String getStartup_Id() { return startup_id; }

    @Keep
    public double getLatitude() {
        return latitude;
    }

    @Keep
    public String getLogobase64() {
        return logobase64;
    }

    @Keep
    public double getLongitude() {
        return longitude;
    }

    @Keep
    public String getSnippet() {
        return snippet;
    }

    @Keep
    public String getTitle() {
        return title;
    }

    @Keep
    public String getUrl() {
        return url;
    }
}