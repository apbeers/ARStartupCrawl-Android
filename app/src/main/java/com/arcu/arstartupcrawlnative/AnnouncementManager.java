package com.arcu.arstartupcrawlnative;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by andrew on 11/27/17.
 */

public class AnnouncementManager {

    private Context context;

    private ArrayList<Announcement> announcements = new ArrayList<>();
    private TinyDB tinydb;

    public ArrayList<Announcement> getAnnouncements(Context context) {

        tinydb = new TinyDB(context);

        return tinydb.getListAnnouncements("announcements");
    }

    public void addAnnouncement(Context context, Announcement a) {

        announcements.clear();
        announcements = tinydb.getListAnnouncements("announcements");
        announcements.add(a);
        Collections.reverse(announcements);
        tinydb = new TinyDB(context);
        tinydb.putListAnnouncements("announcements", announcements);
    }

    private static final AnnouncementManager manager = new AnnouncementManager();
    public static AnnouncementManager getManager() {
        return manager;
    }
}
