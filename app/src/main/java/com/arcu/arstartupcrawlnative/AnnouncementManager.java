package com.arcu.arstartupcrawlnative;

import java.util.ArrayList;

/**
 * Created by andrew on 11/27/17.
 */

public class AnnouncementManager {

    private ArrayList<Announcement> announcements = new ArrayList<>();

    public ArrayList<Announcement> getAnnouncements() {

        announcements.add(new Announcement("myDateTime", "This is my longer Description about the notification to display to the user on the announcement page", "myTitle"));
        announcements.add(new Announcement("myDateTime", "This is my longer Description about the notification to display to the user on the announcement page", "myTitle"));
        announcements.add(new Announcement("myDateTime", "This is my longer Description about the notification to display to the user on the announcement page", "myTitle"));
        announcements.add(new Announcement("myDateTime", "This is my longer Description about the notification to display to the user on the announcement page", "myTitle"));
        announcements.add(new Announcement("myDateTime", "This is my longer Description about the notification to display to the user on the announcement page", "myTitle"));

        return announcements;
    }

    public void setAnnouncements(ArrayList<Announcement> a) {
        this.announcements = a;
    }

    public void addStartup(Announcement a) {
        announcements.add(a);
    }

    public int getCount() {
        return announcements.size();
    }

    private static final AnnouncementManager manager = new AnnouncementManager();
    public static AnnouncementManager getManager() {
        return manager;
    }
}
