package com.arcu.arstartupcrawlnative;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by andrew on 11/26/17.
 */

public class Region {

    private double latitude;
    private double longitude;

    public Region() {

    }

    public Region(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }
}
