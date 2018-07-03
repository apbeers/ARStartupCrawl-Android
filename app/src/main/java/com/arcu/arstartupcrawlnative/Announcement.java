package com.arcu.arstartupcrawlnative;

import android.support.annotation.Keep;

/**
 * Created by andrew on 11/28/17.
 */

@Keep
public class Announcement {

    private String dateTime;
    private String description;
    private String startup;
    private String title;

    @Keep
    public Announcement() {

    }

    @Keep
    public Announcement(String dateTime, String description, String title) {
        this.dateTime = dateTime;
        this.description = description;
        this.title = title;
    }

    @Keep
    public String getDateTime() {
        return dateTime;
    }

    @Keep
    public String getDescription() {
        return description;
    }

    @Keep
    public String getTitle() {
        return title;
    }
}
