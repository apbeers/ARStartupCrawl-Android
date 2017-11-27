package com.arcu.arstartupcrawlnative;

import android.support.annotation.Keep;

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

    public Startup() {

    }

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

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getLogo() {
        return logo;
    }

    public String getLogoBase64() {
        return logoBase64;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getSnippet() {
        return snippet;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }


}
