package com.arcu.arstartupcrawlnative;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Keep;
import android.util.Base64;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by andrew on 11/26/17.
 */

@Keep
public class Startup {


    private String description;
    private int id;
    private double latitude;
    private String logo;
    private String logoBase64;
    private double longitude;
    private String snippet;
    private String title;
    private String url;

    @Keep
    public Startup() {

    }

    @Keep
    public Startup(String description, int id, double latitude, String logo, String logoBase64, double longitude, String snippet, String title, String url) {
        this.description = description;
        this.id = id;
        this.latitude = latitude;
        this.logo = logo;
        this.logoBase64 = logoBase64;
        this.longitude = longitude;
        this.snippet = snippet;
        this.title = title;
        this.url = url;
    }

    @Keep
    public Bitmap getBitmap() {
        byte[] bytes = Base64.decode(this.logoBase64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    @Keep
    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

    @Keep
    public String getDescription() {
        return description;
    }

    @Keep
    public int getId() { return id; }

    @Keep
    public double getLatitude() {
        return latitude;
    }

    @Keep
    public String getLogo() {
        return logo;
    }

    @Keep
    public String getLogoBase64() {
        return logoBase64;
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
